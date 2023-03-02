package com.room4me.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	private static final long EXPIRATION_TIME = 1_800_000;
	private static final String SIGN_IN_KEY = "aa52d27b59c5f8cdc38dbbcfd53f8cac";

	public static String generateToken(String data) {
		return Jwts.builder().setSubject(data)
			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
			.signWith(SignatureAlgorithm.HS512, SIGN_IN_KEY).compact();
	}

	public static String generateToken(Authentication auth) {
		return generateToken(auth.getName());
	}
	
	public static String generateNonExpireToken(Authentication auth) {
		int actualYear = Calendar.getInstance().get(Calendar.YEAR);
		Calendar calendar = Calendar.getInstance();
		calendar.set(actualYear + 1000, 1, 1);

		return Jwts.builder().setSubject(auth.getName())
				.setExpiration(calendar.getTime())
				.signWith(SignatureAlgorithm.HS256, SIGN_IN_KEY).compact();
	}

	public static boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(SIGN_IN_KEY).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static UUID getTokenId(String token) {
		String subject = Jwts.parser().setSigningKey(SIGN_IN_KEY)
            .parseClaimsJws(token).getBody().getSubject();

		return UUID.fromString(subject);
	}

	public static String extractSubject(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public static Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private static Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SIGN_IN_KEY)
            .parseClaimsJws(token).getBody();
	}

	public static Boolean isTokenExpired(String token) {
		Date expiration = extractExpiration(token);

		if(expiration == null) return false;
		return expiration.before(new Date());
	}
}
