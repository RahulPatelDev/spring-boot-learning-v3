package com.rahulpateldev.exception.handler;

import com.rahulpateldev.exception.custom.ResourceAlreadyExistsException;
import com.rahulpateldev.exception.custom.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@ControllerAdvice
public class CustomerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return ResponseEntityBuilder.build(new ApiError(
                LocalDateTime.now(),
                notFound,
                notFound.value(),
                "Resource Not Found",
                ex.getLocalizedMessage())
        );
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        HttpStatus notFound = HttpStatus.CONFLICT;
        return ResponseEntityBuilder.build(new ApiError(
                LocalDateTime.now(),
                notFound,
                notFound.value(),
                "Resource Already Exists",
                ex.getLocalizedMessage())
        );
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return ResponseEntityBuilder.build(new ApiError(
                LocalDateTime.now(),
                notFound,
                notFound.value(),
                "Username not found!",
                ex.getLocalizedMessage())
        );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return ResponseEntityBuilder.build(new ApiError(
                LocalDateTime.now(),
                badRequest,
                badRequest.value(),
                "Error occurred",
                ex.getLocalizedMessage())
        );
    }
}
