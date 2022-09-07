package com.example.sellyourthing.controllers;

import com.example.sellyourthing.datatransferobjects.NewPostRequest;
import com.example.sellyourthing.datatransferobjects.PostReply;
import com.example.sellyourthing.datatransferobjects.UpdatePostRequest;
import com.example.sellyourthing.models.Account;
import com.example.sellyourthing.models.AccountDetails;
import com.example.sellyourthing.models.Post;
import com.example.sellyourthing.services.AccountService;
import com.example.sellyourthing.services.CategoryService;
import com.example.sellyourthing.services.CityService;
import com.example.sellyourthing.services.PostService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

@Controller
@AllArgsConstructor
@RequestMapping("/posts")
@SessionAttributes("message")
public class PostController {
    private final PostService postService;
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final CityService cityService;
    private final Logger logger = LoggerFactory.getLogger(PostController.class);


    @GetMapping("/{postId}")
    public String loadPostPageById(@PathVariable Long postId, Model model, @ModelAttribute("message") HashMap<String, String> message,
                                   HttpServletRequest request) {
        Post currentPost = postService.findByPostId(postId);
        model.addAttribute("currentPost", currentPost);
        model.addAttribute("account", currentPost.getPosterAccount());
        model.addAttribute("replyToPostDto", new PostReply());
        model.addAttribute("currentPostId", currentPost.getPosterAccount().getAccountId());
        ////////////////
        Account postOwner = currentPost.getPosterAccount();
        Authentication auth = (Authentication) request.getUserPrincipal();
        // authAccount = logged-in user. Might be different from owner of the post. In thymeleaf, the div is only shown to USER roles, this can't be null
        AccountDetails authAccount = (AccountDetails) auth.getPrincipal();
        model.addAttribute("isSameUser", postOwner.getAccountId().equals(authAccount.accountId()));
        return "view-post-description";
    }

    @GetMapping("/create-new")
    @PreAuthorize("hasAuthority('USER')")
    public String loadNewPostPage(Model model) {
        model.addAttribute("newPostRequest", new NewPostRequest());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("cities", cityService.findAll());
        return "create-new-post";
    }

    @PostMapping("/create-new")
    @PreAuthorize("hasAuthority('USER')")
    public String processNewPostForm(@ModelAttribute("newPostRequest") @Valid NewPostRequest newPostRequest,
                                     BindingResult errors, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((auth instanceof AnonymousAuthenticationToken)) {
            // user is not authenticated, shouldn't happen since only authenticated users can view the page
            return "/login";
        }
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
    public String replyToPost(@ModelAttribute PostReply postReply, @ModelAttribute("message") HashMap<String, String> message) {
        postService.sendPostReply(postReply);
        message.clear();
        message.put("postReplySuccess", "Your message has been sent");
        return "redirect:/posts/" + postReply.getPostId();
    }

    @PostAuthorize("returnObject.equals()")
    private Long validateAccess() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication auth = securityContext.getAuthentication();
        AccountDetails authAccount = (AccountDetails) auth.getPrincipal();
        return authAccount.accountId();
    }

    @GetMapping("/{postId}/{accountId}/update")
    @PreAuthorize("hasAuthority('USER') and authentication.principal.accountId == #accountId")
    public String loadUpdatePostPage(@PathVariable Long postId, @PathVariable Long accountId, Model model,
                                     @ModelAttribute("message") HashMap<String, String> message) {
        Post postToUpdate = postService.findByPostId(postId);

        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle(postToUpdate.getTitle());
        updatePostRequest.setCategoryName(postToUpdate.getPostCategory().getName());
        updatePostRequest.setBody(postToUpdate.getBody());
        updatePostRequest.setCityName(postToUpdate.getPostCity().getName());
        updatePostRequest.setPrice(postToUpdate.getPrice());


        model.addAttribute("updatePostRequest", updatePostRequest);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("cities", cityService.findAll());
        message.clear();
        message.put("postId", String.valueOf(postId));
        return "update-post";
    }

    @PostMapping("/update")
    public String processUpdatePost(@ModelAttribute("updatePostRequest") @Valid UpdatePostRequest updatePostRequest,
                                    BindingResult errors, Model model, @ModelAttribute("message") HashMap<String, String> message) {
        if (errors.hasErrors()) {
            //adding the categories and cities again because they will be gone after the page is reloaded again
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("cities", cityService.findAll());
            return "update-post";
        }
        Long postId = Long.valueOf(message.get("postId"));
        message.clear();
        postService.update(updatePostRequest, postId);
        message.put("postUpdateSuccessful", "Your post has been updated");
        return "redirect:/posts/" + postId;
    }

    @DeleteMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId, @RequestParam String confirmTitle,
                             @ModelAttribute("message") HashMap<String, String> message,
                             HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        Long authAccountId = ((AccountDetails) auth.getPrincipal()).accountId();
        Post post = postService.findByPostId(postId);
        message.clear();
        if (!authAccountId.equals(postId) || !authAccountId.equals(post.getPostId()) || !postService.existsById(postId)) {
            message.put("forcedLogout", "An error occurred. You have been logged out");
            logger.info("Account id: " + post.getPosterAccount().getAccountId() + " did some funky business with post id " + postId);
            accountService.doManualLogout(request);
            return "redirect:/login";
        }
        if (!post.getTitle().equals(confirmTitle)) {
            logger.info("Account id: " + post.getPosterAccount().getAccountId() + " tried to delete post id " + postId + " with after changing inspect element");
            message.put("postDeleteFailed", "Delete Failed. Don't do that");
            return "redirect:/posts/" + postId;
        }
        postService.delete(postId);
        message.put("deleteSuccess", "Post deleted Successfully");
        return "redirect:/users/" + authAccountId;
    }
}