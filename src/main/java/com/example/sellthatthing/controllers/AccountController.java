package com.example.sellthatthing.controllers;

import com.example.sellthatthing.DTOs.NewAccountRequest;
import com.example.sellthatthing.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    // Register Requests
    @GetMapping("/register")
    public String loadRegisterPage(final Model model) {
        model.addAttribute("registrationDto", new NewAccountRequest());
        return "register";
    }

    @PostMapping("/register")
    public String processRegisterForm(@ModelAttribute final NewAccountRequest newAccountRequest) {
        accountService.createAccount(newAccountRequest);
        return "redirect:/register?success"; // redirect user to login page after registration
    }

    @GetMapping("/register/verify")
    public String completeRegistration(@RequestParam final String token) {
        accountService.confirmToken(token);
        return "verify-success";
    }

    // Login Requests
    @GetMapping("/login")
    public String loadLoginPage() {
        return "login";
    }
}
