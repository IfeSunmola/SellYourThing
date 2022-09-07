package com.example.sellyourthing.controllers.Admin;

import com.example.sellyourthing.models.AccountDetails;
import com.example.sellyourthing.models.Post;
import com.example.sellyourthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/*
 * For everything relating to the admin managing posts
 * */
@Controller
@AllArgsConstructor
@RequestMapping("/admin/posts")
@SessionAttributes("message")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminPostController {
    private final PostService postService;

    /*
     * Get the currently logged-in user
     * */
    private AccountDetails getAuthAccount(HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        return (AccountDetails) auth.getPrincipal();
    }

    /*
     * loads the page that shows all posts
     * */
    @GetMapping
    public String getPosts(Model model, HttpServletRequest request, @ModelAttribute("message") HashMap<String, String> message) {
        model.addAttribute("allPosts", postService.findAll());
        AccountDetails authAccount = getAuthAccount(request);
        model.addAttribute("authAccountId", authAccount.accountId().toString()); // toString for javascript
        model.addAttribute("authAccountEmail", authAccount.email());
        return "admin/admin-view-posts";
    }

    /*
     * Find a post by the post id and return a response body, i.e. JSON
     * The response body is used in jquery to enable the delete button and do some things I can't remember
     * */
    @GetMapping("/find/{postId}")
    @ResponseBody
    public HashMap<String, String> getPostById(@PathVariable Long postId, HttpServletRequest request) {
        Post post = postService.findByPostId(postId);
        AccountDetails authAccount = getAuthAccount(request);
        HashMap<String, String> deletePostInfo = new HashMap<>(4);
        deletePostInfo.put("authId", authAccount.accountId().toString());
        deletePostInfo.put("authEmail", authAccount.email());
        deletePostInfo.put("postId", post.getPostId().toString());
        deletePostInfo.put("postPrice", post.getPrice().toString());
        return deletePostInfo;
    }

    /*
    * Delete a post by its id
    * */
    @DeleteMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId, @ModelAttribute("message") HashMap<String, String> message) {
        System.out.println("Deleting post: " + postId);
        postService.adminDelete(postId, message);
        return "redirect:/admin/posts";
    }
}
