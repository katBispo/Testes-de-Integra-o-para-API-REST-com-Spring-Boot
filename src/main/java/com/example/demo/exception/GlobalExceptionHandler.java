package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FreteException.class)
    public ResponseEntity<String> handleFreteException(FreteException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
