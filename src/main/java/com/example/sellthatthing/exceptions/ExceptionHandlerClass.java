package com.example.sellthatthing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
/*
 * This class handles any exception thrown from the CategoryController class
 * */
public class ExceptionHandlerClass {
    @ExceptionHandler
    public ResponseEntity<HashMap<String, String>> resourceAlreadyExists(ResourceAlreadyExistsException ex) {
        HashMap<String, String> errorMessage = new HashMap<>(1);
        errorMessage.put("Error", ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<HashMap<String, String>> resourceNotFound(ResourceNotFoundException ex) {
        HashMap<String, String> errorMessage = new HashMap<>(1);
        errorMessage.put("Error", ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<HashMap<String, String>> emptyResource(EmptyResourceException ex) {
        HashMap<String, String> errorMessage = new HashMap<>(1);
        errorMessage.put("Error", ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
