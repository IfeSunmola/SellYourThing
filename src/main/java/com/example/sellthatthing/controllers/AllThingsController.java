package com.example.sellthatthing.controllers;

import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AllThingsController {
    private final PostService postService;

    @GetMapping("/all")
    public String showAllPosts(Model model){
        model.addAttribute("allPosts", postService.findAll());
        return "all-things-template";
    }
}
