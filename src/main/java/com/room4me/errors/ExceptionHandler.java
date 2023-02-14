package com.room4me.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException exception, HttpHeaders headers,
        HttpStatusCode status, WebRequest request
    ) {
        String message = "";
        for(ObjectError error : exception.getBindingResult().getAllErrors()) {
            message += error.getDefaultMessage() + "\n";
        }
        
		return handleExceptionInternal(exception, message, headers, status, request);
	}
}