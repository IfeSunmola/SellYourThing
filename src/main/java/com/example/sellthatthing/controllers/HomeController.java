package com.example.sellthatthing.controllers;

import com.example.sellthatthing.services.AccountService;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    private final PostService postService;
    private final AccountService accountService;

    @GetMapping
    public String showIndexPage(final Model model) {
        model.addAttribute("posts", postService.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) { // user is authenticated
            // for some reason, I can't construct the link in thymeleaf with th:href="|@{/users/${#authentication.principal.accountId}}|",
            // so I have to do it here
            String email = auth.getName();
            String profileLink = "/users/" + accountService.findByEmail(email).getAccountId();
            model.addAttribute("profileLink", profileLink);
        }
        return "index";
    }

    @GetMapping("/css/style.css")
    public String temp() {
        return "redirect:/";
    }
}
