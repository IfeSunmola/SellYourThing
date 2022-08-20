package com.example.sellthatthing.Post;

import com.example.sellthatthing.Category.CategoryService;
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
        return "view-post";
    }

    @GetMapping("/create-new")
    public String loadNewPostPage(Model model) {
        model.addAttribute("newPostDto", new NewPostRequest());
        model.addAttribute("categories", categoryService.findAll());
        return "create-new-post";
    }

    @PostMapping("/create-new")
    public String processNewPostForm(@ModelAttribute NewPostRequest newPostRequest) {
        postService.createNewPost(newPostRequest);
        return "redirect:/all";
    }
}
