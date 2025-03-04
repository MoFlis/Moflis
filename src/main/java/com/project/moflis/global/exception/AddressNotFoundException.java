package com.project.moflis.global.exception;

import org.springframework.http.HttpStatus;

public class AddressNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public AddressNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}