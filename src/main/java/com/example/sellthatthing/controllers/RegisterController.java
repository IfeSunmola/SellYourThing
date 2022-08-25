package com.example.sellthatthing.controllers;

import com.example.sellthatthing.datatransferobjects.NewAccountRequest;
import com.example.sellthatthing.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final AccountService accountService;

    @GetMapping
    public String loadRegisterPage(final Model model) {
        model.addAttribute("registrationDto", new NewAccountRequest());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) { // user is authenticated
            // for some reason, I can't construct the link in thymeleaf with th:href="|@{/users/${#authentication.principal.accountId}}|",
            // so I have to do it here
            String email = auth.getName();
            String profileLink = "/profile/" + accountService.findByEmail(email).getAccountId();
            model.addAttribute("profileLink", profileLink);
        }
        return "register";
    }

    @PostMapping
    public String processRegisterForm(@ModelAttribute final NewAccountRequest newAccountRequest, final HttpServletRequest request) {
        accountService.createAccount(newAccountRequest, request);
        return "redirect:/register?success"; // redirect user to login page after registration
    }

    @GetMapping("/verify")
    public String completeRegistration(@RequestParam final String token) {
        accountService.confirmToken(token);
        return "verify-success";
    }
}
