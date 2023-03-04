package com.room4me.errors;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ExceptionResponse {
    private HttpStatus error;
    private String timestamp;
    private String message;
    private int status;

    public ExceptionResponse(String message, HttpStatus error) {
        this.error = error;
        this.status = error.value();
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    public static ExceptionResponse FromServerException(ServerException exception) {
        HttpStatus httpCode = exception.getHttpCode();
        String message = exception.getMessage();

        return new ExceptionResponse(message, httpCode);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getError() {
        return error;
    }

    public void setError(HttpStatus error) {
        this.error = error;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }   
}
