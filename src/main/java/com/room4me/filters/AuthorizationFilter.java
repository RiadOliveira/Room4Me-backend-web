package com.room4me.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.room4me.errors.ExceptionResponse;
import com.room4me.services.AuthenticationServices;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
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
            parseFilterException(request, response, exception);
        }
    }

    private void parseFilterException(
		HttpServletRequest request, HttpServletResponse response,
        Exception exception
	) throws ServletException, IOException {
        String parsedMessage = exception.getMessage().split(" Current")[0];
        ExceptionResponse parsedException = new ExceptionResponse(
            HttpStatus.FORBIDDEN, parsedMessage
        );
            
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(403);
		response.getWriter().write(
			mapper.writeValueAsString(parsedException)
		);
	}
}
