package com.example.sellthatthing.controllers;

import com.example.sellthatthing.DTOs.NewAccountRequest;
import com.example.sellthatthing.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RegisterController {
    private final AccountService accountService;

    @GetMapping("/register")
    public String loadRegisterPage(Model model){
        model.addAttribute("registrationDto", new NewAccountRequest());
        return "register";
    }

    @PostMapping("/registration")
    public String processRegisterForm(@ModelAttribute NewAccountRequest newAccountRequest){
        accountService.createAccount(newAccountRequest);
        return "redirect:/login"; // redirect user to login page after registration
    }
}
