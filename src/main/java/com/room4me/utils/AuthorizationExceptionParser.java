package com.room4me.utils;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.room4me.errors.ExceptionResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationExceptionParser {
    public static void parseAuthorizationException(
		HttpServletRequest request, HttpServletResponse response,
        Exception exception
	) throws ServletException, IOException {
        String parsedMessage; 
        HttpStatus status;

        if(response.getStatus() == 404) {
            parsedMessage = "Route not found";
            status = HttpStatus.NOT_FOUND;
        } else {
            parsedMessage = exception.getMessage().split(" Current")[0];
            status = HttpStatus.UNAUTHORIZED;
        }

        ExceptionResponse parsedException = new ExceptionResponse(
            status, parsedMessage
        );
            
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(status.value());

		response.getWriter().write(
			mapper.writeValueAsString(parsedException)
		);
	}
}
