package com.room4me.errors;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ExceptionResponse> handleServerException(
        ServerException exception, WebRequest request
    ) {
        ExceptionResponse errorResponse = ExceptionResponse.FromServerException(exception);
        return new ResponseEntity<>(errorResponse, exception.getHttpCode());
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
		MethodArgumentNotValidException exception, HttpHeaders headers,
        HttpStatusCode status, WebRequest request
    ) {
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        String message = "";

        for(int ind=0 ; ind<errors.size() ; ind++) {
            if(ind > 0) message += " | ";
            message += errors.get(ind).getDefaultMessage();
        }

        ExceptionResponse errorResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST, message);
		return new ResponseEntity<>(errorResponse, status);
	}
}