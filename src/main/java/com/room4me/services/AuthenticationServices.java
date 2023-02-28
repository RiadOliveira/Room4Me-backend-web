package com.room4me.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.room4me.entities.User;
import com.room4me.repositories.UserRepository;
import com.room4me.utils.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;
import static java.util.Collections.emptyList;

@Service
public class AuthenticationServices implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User findedUser = userRepository.findByEmail(email);
        if(findedUser == null) {
            throw new UsernameNotFoundException("Invalid e-mail or password");
        }

        UserDetails userDetails = new org.springframework.security.core
            .userdetails.User(
                findedUser.getId().toString(), findedUser.getPassword(),
                AuthorityUtils.createAuthorityList("USER")
            );
        return userDetails;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null) return null;

        String token = authorizationHeader.split(" ")[1];
        if(token.isEmpty()) return null;

        UUID userId = JwtUtils.getTokenId(token);
        if(userId == null) return null;

        return new UsernamePasswordAuthenticationToken(
            userId, null, emptyList()
        );
    }
}
