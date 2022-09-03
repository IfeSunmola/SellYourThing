package com.example.sellthatthing.controllers;

import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.models.AccountDetails;
import com.example.sellthatthing.models.Post;
import com.example.sellthatthing.services.AccountService;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
@SessionAttributes("message")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final AccountService accountService;
    private final PostService postService;

    private AccountDetails getAuthAccount(HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        return (AccountDetails) auth.getPrincipal();
    }

    // accounts
    @GetMapping("/findAccount/{id}")
    @ResponseBody
    public HashMap<String, String> getAccountById(@PathVariable Long id) {
        Account account = accountService.findByAccountId(id);
        HashMap<String, String> accountInfo = new HashMap<>(7);
        accountInfo.put("accountId", account.getAccountId().toString());
        accountInfo.put("firstName", account.getFirstName());
        accountInfo.put("lastName", account.getLastName());
        accountInfo.put("email", account.getEmail());
        String formattedDate = account.getDateOfBirth().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        accountInfo.put("dateOfBirth", formattedDate);
        accountInfo.put("role", account.getRole());
        accountInfo.put("active", (account.isEnabled() ? "YES" : "NO"));
        return accountInfo;
    }

    @GetMapping("/accounts")
    public String viewAllAccounts(Model model, HttpServletRequest request, @ModelAttribute("message") HashMap<String, String> message) {
        model.addAttribute("allAccounts", accountService.findAll());

        AccountDetails authAccount = getAuthAccount(request);
        model.addAttribute("authAccountId", authAccount.accountId().toString()); // toString for javascript
        model.addAttribute("authAccountEmail", authAccount.email());
        return "admin-view-account";
    }

    @PatchMapping("/{accountId}/disable")
    public String disableAccount(@PathVariable Long accountId, @ModelAttribute("message") HashMap<String, String> message) {
        accountService.disableById(accountId, message); // log the user out too
        return "redirect:/admin/accounts";
    }

    @PatchMapping("/{accountId}/enable")
    public String enableAccount(@PathVariable Long accountId, @ModelAttribute("message") HashMap<String, String> message) {
        accountService.enableById(accountId, message);
        return "redirect:/admin/accounts";
    }

    @DeleteMapping("/{accountId}/delete")
    public String deleteAccount(@PathVariable Long accountId, @ModelAttribute("message") HashMap<String, String> message) {
        accountService.deleteById(accountId, message);
        return "redirect:/admin/accounts";
    }

    // posts
    @GetMapping("/posts")
    public String getPosts(Model model, HttpServletRequest request, @ModelAttribute("message") HashMap<String, String> message) {
        model.addAttribute("allPosts", postService.findAll());
        AccountDetails authAccount = getAuthAccount(request);
        model.addAttribute("authAccountId", authAccount.accountId().toString()); // toString for javascript
        model.addAttribute("authAccountEmail", authAccount.email());
        return "admin-view-posts";
    }

    @GetMapping("/findPost/{postId}")
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

    @DeleteMapping("/{postId}/deletePost")
    public String deletePost(@PathVariable Long postId, @ModelAttribute("message") HashMap<String, String> message){
        System.out.println("Deleting post: " + postId);
        postService.adminDelete(postId, message);
        return "redirect:/admin/posts";
    }
}
