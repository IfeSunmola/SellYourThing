package com.example.sellthatthing.controllers;

import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public String loadProfilePage(@PathVariable Long accountId, Model model) {
        Account account = accountService.findByAccountId(accountId);
        model.addAttribute("accountInfo", account);
        return "profile";
    }

    @DeleteMapping("/{accountId}/delete")
    public String deleteAccount(@PathVariable Long accountId) {
        accountService.delete(accountId);
        return "redirect:/";
    }

    @PatchMapping("/{accountId}/update")
    public String updateAccount(@PathVariable Long accountId) {
        accountService.update(null, accountId);
        return "redirect:/";
    }

    @GetMapping("/{accountId}/posts")
    public String getAllUserPosts(@PathVariable Long accountId, Model model) {
        model.addAttribute("allUserPosts", accountService.findPostsByAccountId(accountId));
        return "view-post";
    }
}
