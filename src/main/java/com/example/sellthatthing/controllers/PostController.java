package com.example.sellthatthing.controllers;

import com.example.sellthatthing.datatransferobjects.NewPostRequest;
import com.example.sellthatthing.datatransferobjects.PostReply;
import com.example.sellthatthing.models.Post;
import com.example.sellthatthing.services.CategoryService;
import com.example.sellthatthing.services.CityService;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    private final CategoryService categoryService;
    private final CityService cityService;

    @GetMapping("/{postId}")
    public String loadPostPageById(@PathVariable final Long postId, Model model) {
        Post currentPost = postService.findByPostId(postId);
        model.addAttribute("currentPost", currentPost);
        model.addAttribute("account", currentPost.getPosterAccount());
        model.addAttribute("replyToPostDto", new PostReply());
        return "view-post-description";
    }

    @GetMapping("/create-new")
    public String loadNewPostPage(final Model model) {
        model.addAttribute("newPostRequest", new NewPostRequest());
        model.addAttribute("categories", categoryService.findAll());

        model.addAttribute("cities", cityService.findAll());
        return "create-new-post";
    }

    @PostMapping("/create-new")
    public String processNewPostForm(@ModelAttribute("newPostRequest") @Valid NewPostRequest newPostRequest,
                                     BindingResult errors, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((auth instanceof AnonymousAuthenticationToken)) {
            // user is not authenticated, shouldn't happen since only authenticated users can view the page
            return "/login";
        }
        postService.checkForErrors(newPostRequest, errors);
        if (errors.hasErrors()) {
            //adding the categories and cities again because they will be gone after the page is reloaded again
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("cities", cityService.findAll());
            return "create-new-post";
        }
        postService.createNewPost(newPostRequest, auth);
        return "redirect:/";
    }

    @PostMapping("/reply")
    public String replyToPost(@ModelAttribute PostReply postReply) {
        postService.sendPostReply(postReply);
        return "redirect:/posts/" + postReply.getPostId() + "?replySuccess";
    }
}