package com.example.sellthatthing.controllers;

import com.example.sellthatthing.services.AccountService;
import com.example.sellthatthing.services.CategoryService;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class IndexController {
    private final CategoryService categoryService;
    private final PostService postService;
    private final AccountService accountService;

    @GetMapping
    public String showIndexPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("email", "");
        return "index";
    }

    @PostMapping("/email")
    public String validateEmail(@RequestParam String emailDto) {
        if (accountService.existsByEmail(emailDto)) {
            return "redirect:/login";
        }
        else {
            return "redirect:/register";
        }
    }
}
