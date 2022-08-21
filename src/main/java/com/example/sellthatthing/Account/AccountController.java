package com.example.sellthatthing.Account;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    // Register Requests
    @GetMapping("/register")
    public String loadRegisterPage(final Model model) {
        model.addAttribute("registrationDto", new NewAccountRequest());
        return "register";
    }

    @PostMapping("/register")
    public String processRegisterForm(@ModelAttribute final NewAccountRequest newAccountRequest) {
        accountService.createAccount(newAccountRequest);
        return "redirect:/register?success"; // redirect user to login page after registration
    }

    @GetMapping("/register/verify")
    public String completeRegistration(@RequestParam final String token) {
        accountService.confirmToken(token);
        return "verify-success";
    }

    // Login Requests
    @GetMapping("/login")
    public String loadLoginPage() {
        return "login";
    }

    // Profile Requests
    @GetMapping("/users/{accountId}")
    public String loadProfilePage(@PathVariable Long accountId, Model model) {
        Account account = accountService.findById(accountId);
        model.addAttribute("accountInfo", account);
        return "profile";
    }

    @DeleteMapping("/users/{accountId}/delete")
    public String deleteAccount(@PathVariable Long accountId){
        accountService.delete(accountId);;
        return "redirect:/";
    }

    @PatchMapping ("/users/{accountId}/update")
    public String updateAccount(@PathVariable Long accountId){
        accountService.update(null, accountId);
        return "redirect:/";
    }

    @GetMapping("/users/{accountId}/posts")
    public String getAllUserPosts(@PathVariable Long accountId, Model model){
        model.addAttribute("allUserPosts", accountService.findPostsByAccountId(accountId));
        return "view-post";
    }
}
