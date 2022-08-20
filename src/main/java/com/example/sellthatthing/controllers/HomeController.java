package com.example.sellthatthing.controllers;

import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    private final PostService postService;

    @GetMapping
    public String showIndexPage(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "index";
    }
}
