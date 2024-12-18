package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
               
        return new ResponseEntity<>(
            "{\"error\": \"Internal Server Error : " + ex.getMessage() + "\"}",
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(NoProjectFoundException.class)
    public ResponseEntity<String> handleNoProjectFoundException(NoProjectFoundException ex) {
        return new ResponseEntity<>(
            "{\"error\": \" " + ex.getMessage() + "\"}",
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(TokenLimitExceeded.class)
    public ResponseEntity<String> handleTokenLimitExceeded(TokenLimitExceeded ex) {
        // Custom message for "Token Limit Exceeded" error
        return new ResponseEntity<>(
            "{\"error\": \" " + ex.getMessage() + "\"}",
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(FailedToExecuteTokens.class)
    public ResponseEntity<String> handleFailedToExecuteTokens(FailedToExecuteTokens ex) {
        // Custom message for "Failed to Execute Tokens" error
        return new ResponseEntity<>(
            "{\"error\": \" " + ex.getMessage() + "\"}",
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}