package com.example.sellthatthing.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute("message")
    public HashMap<String, String> message() {
        return new HashMap<>(1);
    }
}
