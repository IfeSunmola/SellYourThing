package com.example.sellthatthing.controllers;

import com.example.sellthatthing.datatransferobjects.NewPostRequest;
import com.example.sellthatthing.models.Post;
import com.example.sellthatthing.services.AccountService;
import com.example.sellthatthing.services.CategoryService;
import com.example.sellthatthing.services.LocationService;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final LocationService locationService;

    @GetMapping("/{postId}")
    public String loadPostPageById(@PathVariable Long postId, Model model) {
        Post currentPost = postService.findByPostId(postId);
        model.addAttribute("currentPost", currentPost);
        model.addAttribute("account", currentPost.getPosterAccount());
        return "view-post-description";
    }

    @GetMapping("/create-new")
    public String loadNewPostPage(Model model) {
        model.addAttribute("newPostDto", new NewPostRequest());
        model.addAttribute("categories", categoryService.findAll());

        model.addAttribute("locations", locationService.findAll());
        return "create-new-post";
    }

    @PostMapping("/create-new")
    public String processNewPostForm(@ModelAttribute NewPostRequest newPostRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((auth instanceof AnonymousAuthenticationToken)) {
            // user is not authenticated, shouldn't happen since only authenticated users can view the page
            return "redirect:/login";
        }
        postService.createNewPost(newPostRequest, auth);
        return "redirect:/";
    }
}
