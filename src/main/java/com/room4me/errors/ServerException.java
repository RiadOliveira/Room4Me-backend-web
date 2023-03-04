package com.room4me.errors;

import org.springframework.http.HttpStatus;

public class ServerException extends RuntimeException {
    public static final ServerException INTERNAL_SERVER_EXCEPTION = 
        new ServerException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

    private String message;
    private HttpStatus httpCode;

    public ServerException(String message, HttpStatus httpCode) {
        this.message = message;
        this.httpCode = httpCode;
    }

    public ServerException(String message) {
        this.message = message;
        this.httpCode = HttpStatus.BAD_REQUEST;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(HttpStatus httpCode) {
        this.httpCode = httpCode;
    }
}
