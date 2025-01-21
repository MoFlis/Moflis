package com.project.moflis.exception;

import org.springframework.http.HttpStatus;

public class UserLocationAlreadyExistsException extends RuntimeException {
    private final HttpStatus status;

    public UserLocationAlreadyExistsException(String message) {
        super(message);
        this.status = HttpStatus.CONFLICT;
    }

    public HttpStatus getStatus() {
        return status;
    }
}