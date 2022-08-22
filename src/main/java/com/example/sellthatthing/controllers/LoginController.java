package com.example.sellthatthing.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String loadLoginPage() {
        return "login";
    }
}
