package com.example.sellthatthing.controllers;

import com.example.sellthatthing.datatransferobjects.PostsSortDto;
import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.models.AccountDetails;
import com.example.sellthatthing.services.AccountService;
import com.example.sellthatthing.services.CategoryService;
import com.example.sellthatthing.services.CityService;
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
@RequestMapping("/users")
public class ProfileController {
    private final AccountService accountService;
    private final PostService postService;
    private final CityService cityService;
    private final CategoryService categoryService;


    @GetMapping("/{accountId}")
    public String loadProfilePage(@PathVariable Long accountId, Model model) {
        Account currentAccount = accountService.findByAccountId(accountId);
        model.addAttribute("currentAccount", currentAccount);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) { // user is authenticated
            AccountDetails authAccount = (AccountDetails) auth.getPrincipal();
            model.addAttribute("authAccount", authAccount);
            model.addAttribute("isSameUser", authAccount.accountId().equals(currentAccount.getAccountId()));
        }
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
    public String getAllUserPosts(@PathVariable Long accountId, Model model, @ModelAttribute("postsSortDto") PostsSortDto postsSortDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) { // user is authenticated
            Account authAccount = (Account) auth.getPrincipal();
            model.addAttribute("authAccount", authAccount);
            // model.addAttribute("isSameUser", authAccount.getAccountId().equals(currentAccount.getAccountId()));
        }

        String cityName = postsSortDto.getCity();
        String categoryName = postsSortDto.getCategory();
        String order = postsSortDto.getOrder();
        String searchText = postsSortDto.getSearchText();

        model.addAttribute("userPosts", postService.usersPost(accountId, cityName, categoryName, order, searchText));
        model.addAttribute("cities", cityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "view-user-posts";
    }
}
