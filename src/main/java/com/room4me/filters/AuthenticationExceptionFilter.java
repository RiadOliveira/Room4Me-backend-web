package com.room4me.filters;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.room4me.utils.AuthorizationExceptionParser;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationExceptionFilter implements AuthenticationEntryPoint {
    @Override
    public void commence(
        HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception
    ) throws IOException, ServletException {
        AuthorizationExceptionParser.parseAuthorizationException(
            request, response, exception
        );
    }
}