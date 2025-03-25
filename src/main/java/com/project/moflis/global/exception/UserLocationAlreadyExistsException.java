package com.project.moflis.global.exception;

public class UserLocationAlreadyExistsException extends RuntimeException {

    public UserLocationAlreadyExistsException(String message) {
        super(message);
    }
}