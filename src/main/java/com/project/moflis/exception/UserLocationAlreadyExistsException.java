package com.project.moflis.exception;

public class UserLocationAlreadyExistsException extends RuntimeException {

    public UserLocationAlreadyExistsException(String message) {
        super(message);
    }
}