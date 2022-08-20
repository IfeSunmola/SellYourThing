package com.example.sellthatthing.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class LoginController {
    @GetMapping("/login")
    public String loadLoginPage(){
        return "login";
    }
}
