package com.room4me.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.room4me.services.AuthenticationServices;
import com.room4me.utils.AuthorizationExceptionParser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationServices authenticationServices;

    @Override
    public void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain
    ) throws IOException, ServletException {
        try {
            Authentication authentication = authenticationServices.getAuthentication(
                (HttpServletRequest) request
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            if(authentication != null) {
                request.setAttribute("userId", authentication.getPrincipal());
            }
    
            filterChain.doFilter(request, response);
        } catch(Exception exception) {
            AuthorizationExceptionParser.parseAuthorizationException(
                request, response, exception
            );
        }
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String method = request.getMethod();
        if(method.equals("GET")) return true;

        String path = request.getRequestURI();
        if(method.equals("POST") && path.startsWith("/user")) return true;

        return false;
	}
}
