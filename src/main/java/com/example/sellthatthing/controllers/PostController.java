package com.example.sellthatthing.controllers;

import com.example.sellthatthing.models.Post;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts/{postId}")
    public String loadPostPageById(@PathVariable Long postId, Model model){
        Post currentPost = postService.findByPostId(postId);
        model.addAttribute("currentPost", currentPost);
        model.addAttribute("account", currentPost.getPosterAccount());
        return "posts-id";
    }
}
