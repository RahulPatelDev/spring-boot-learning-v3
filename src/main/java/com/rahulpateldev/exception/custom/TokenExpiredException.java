package com.rahulpateldev.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String token, String message) {
        super(String.format("Token Expired [%s], %s", token, message));
    }
}
