package com.rahulpateldev.exception.custom;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserRegistrationException extends RuntimeException {
    public UserRegistrationException(String username, String message) {
        super(String.format("Failed for [%s]: %s", username, message));
    }
}
