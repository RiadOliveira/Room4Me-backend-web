package com.room4me.filters;

import java.io.IOException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.room4me.entities.User;
import com.room4me.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SessionsFilter extends AbstractAuthenticationProcessingFilter {
    public SessionsFilter(
        String url, AuthenticationManager authenticationManager
    ) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request, HttpServletResponse response
    ) throws AuthenticationException, IOException, ServletException {
        User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

        return getAuthenticationManager().authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()
            )
        );
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain, Authentication authentication
    ) throws IOException, ServletException {
        String token = JwtUtils.generateToken(authentication);

        response.addHeader("Authorization",
            JwtUtils.TOKEN_PREFIX + " " + token
        );
        response.addHeader(
            "Access-Control-Expose-Headers",
            "Authorization"
        );
    }
}
