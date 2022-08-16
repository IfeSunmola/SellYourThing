package com.example.sellthatthing.controllers;

import com.example.sellthatthing.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class IndexController {
    private final CategoryService categoryService;

    @GetMapping
    public String showIndexPage(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }
}
