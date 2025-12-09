package com.shoppingbackend.flywheel_store.exceptions;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenyException(AccessDeniedException exception){
        String message = "You don't have permission to this action";
        return new ResponseEntity<>(message, FORBIDDEN);
    }
}