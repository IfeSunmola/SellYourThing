package com.example.sellthatthing.controllers;

import com.example.sellthatthing.datatransferobjects.PostsSortDto;
import com.example.sellthatthing.datatransferobjects.UpdateAccountRequest;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
@SessionAttributes("message")
public class ProfileController {
    private final AccountService accountService;
    private final PostService postService;
    private final CityService cityService;
    private final CategoryService categoryService;

    private String redirectToHome() {
        return "redirect:/";
    }

    @GetMapping("/{accountId}")
    public String loadProfilePage(@PathVariable Long accountId, Model model, @ModelAttribute("message") HashMap<String, String> message) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isSameUser; // if the user viewing the page is the same as the logged-in user
        Account currentAccount = accountService.findByAccountId(accountId); // user viewing the page
        AccountDetails authAccount = null; // logged in user
        boolean isAuthenticated = !(auth instanceof AnonymousAuthenticationToken);

        model.addAttribute("currentAccount", currentAccount);
        if (isAuthenticated) {
            authAccount = (AccountDetails) auth.getPrincipal();
            isSameUser = authAccount.accountId().equals(currentAccount.getAccountId());

            model.addAttribute("authAccount", authAccount);
            model.addAttribute("isSameUser", isSameUser);

            if (isSameUser) {//
                UpdateAccountRequest updateInfo = new UpdateAccountRequest();
                updateInfo.setFirstName(authAccount.firstName());
                updateInfo.setLastName(authAccount.lastName());
                updateInfo.setEmail(authAccount.email());
                updateInfo.setDateOfBirth(authAccount.dateOfBirth());
                model.addAttribute("updateAccountDto", updateInfo);
            }
        }
        return "profile";
    }

    @GetMapping("/update-account")
    public String getUpdateAccount() {
        return redirectToHome();
    }

    @PatchMapping("/update-account")
    public String updateAccount(@ModelAttribute UpdateAccountRequest updateInfo,
                                @ModelAttribute("message") HashMap<String, String> message) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((auth instanceof AnonymousAuthenticationToken)) { // not authenticated
            return "/login";
        }
        AccountDetails accountDetails = (AccountDetails) auth.getPrincipal();
        accountService.updateAccount(updateInfo, message, accountDetails);
        return "redirect:/users/" + accountDetails.accountId();
    }

    @GetMapping("/delete-account")
    public String getDeleteAccount() {
        return redirectToHome();
    }

    /*
     * Flow:
     * If for some reason the user is not authenticated, redirect to login page
     * Go to accountService to delete the account, invalidate the session and log the user out
     * If the account was successfully deleted, redirect to login page.
     * If not, redirect to users profile page and show error message.
     * Check README for how the @ModelAttribute is working
     * */
    @DeleteMapping("/delete-account")
    public String deleteAccount(@RequestParam String confirmEmail,
                                @ModelAttribute("message") HashMap<String, String> message,
                                HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        if ((auth instanceof AnonymousAuthenticationToken)) { // not authenticated
            return "/login";
        }
        AccountDetails accountDetails = (AccountDetails) auth.getPrincipal();
        boolean deleted = accountService.delete(confirmEmail, accountDetails, request, response);

        message.remove("deleteStatus");
        if (deleted) {
            message.put("deleteStatus", "true");
            return "redirect:/";
        }
        message.put("deleteStatus", "false");
        return "redirect:/users/" + accountDetails.accountId();
    }


    @GetMapping("/{accountId}/posts")
    public String getAllUserPosts(@PathVariable Long accountId, Model model, @ModelAttribute("postsSortDto") PostsSortDto postsSortDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) { // user is authenticated
            Account authAccount = ((AccountDetails) auth.getPrincipal()).account();
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
