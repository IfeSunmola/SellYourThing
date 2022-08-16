package com.example.sellthatthing.controllers;

import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private final PostService postService;

    @GetMapping("/{categoryName}")
    public String showPostsInCategory(@PathVariable String categoryName, Model model){
        model.addAttribute("name", categoryName);
        model.addAttribute("allPosts", postService.findByPostCategory(categoryName));
        return "category-post-template";
    }
}
