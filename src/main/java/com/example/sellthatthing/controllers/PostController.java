package com.example.sellthatthing.controllers;

import com.example.sellthatthing.DTOs.NewPostRequest;
import com.example.sellthatthing.models.Post;
import com.example.sellthatthing.services.CategoryService;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("/{postId}")
    public String loadPostPageById(@PathVariable Long postId, Model model) {
        Post currentPost = postService.findByPostId(postId);
        model.addAttribute("currentPost", currentPost);
        model.addAttribute("account", currentPost.getPosterAccount());
        return "posts-id";
    }

    @GetMapping("/create-new")
    public String loadNewPostPage(Model model) {
        model.addAttribute("newPostDto", new NewPostRequest());
        model.addAttribute("categories", categoryService.findAll());
        return "new-listing";
    }

    @PostMapping("/create-new")
    public String processNewPostForm(@ModelAttribute NewPostRequest newPostRequest) {
        postService.createNewPost(newPostRequest);
        return "redirect:/all";
    }
}
