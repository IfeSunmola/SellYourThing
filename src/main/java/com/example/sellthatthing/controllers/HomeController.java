package com.example.sellthatthing.controllers;

import com.example.sellthatthing.services.CityService;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    private final PostService postService;
    private final CityService cityService;

    @GetMapping
    public String showIndexPage(final Model model) {
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("cities", cityService.findAll());
        return "index";
    }
}
