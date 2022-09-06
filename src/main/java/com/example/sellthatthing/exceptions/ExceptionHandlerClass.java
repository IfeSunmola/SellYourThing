package com.example.sellthatthing.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

/**
 * @author Ife Sunmola
 * <p>
 * This class handles any exception thrown.
 */

@ControllerAdvice
public class ExceptionHandlerClass {
    @ExceptionHandler
    public ResponseEntity<HashMap<String, String>> resourceAlreadyExists(ResourceAlreadyExistsException ex) {
        HashMap<String, String> errorMessage = new HashMap<>(1);
        errorMessage.put("Error", ex.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * User tried uploading an image that's too big
     */
    @ExceptionHandler(MultipartException.class)
    public String handleMultipartException(MultipartException ex) {
        return "error-pages/405-error";
    }

    /**
     * Something was not found. Return 404 page
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public String resourceNotFound() {
        return "error-pages/404-error";
    }
}
