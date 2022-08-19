package com.example.sellthatthing.controllers;

import com.example.sellthatthing.DTOs.NewPostRequest;
import com.example.sellthatthing.services.CategoryService;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class NewListingController {
    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("/new-listing")
    public String loadNewPostPage(Model model){
        model.addAttribute("newPostDto", new NewPostRequest());
        model.addAttribute("categories", categoryService.findAll());
        return "new-listing";
    }

    @PostMapping("/new-post")
    public String processNewPostForm(@ModelAttribute NewPostRequest newPostRequest){
        postService.createNewPost(newPostRequest);
        return "redirect:/all";
    }
}