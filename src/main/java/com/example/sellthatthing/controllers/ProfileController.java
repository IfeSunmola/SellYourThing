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

import javax.servlet.http.HttpSession;
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

    @ModelAttribute("message")
    public HashMap<String, Boolean> message() {
        return new HashMap<>(1);
    }

    @GetMapping("/{accountId}")
    public String loadProfilePage(@PathVariable Long accountId, Model model) {
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
    public String redirectToProfile(){
        return "redirect:/";
    }

    @PatchMapping("/update-account")
    public String updateAccount(@ModelAttribute UpdateAccountRequest updateInfo,
                                @ModelAttribute("message") HashMap<String, Boolean> message) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((auth instanceof AnonymousAuthenticationToken)) { // not authenticated
            return "/login";
        }
        AccountDetails accountDetails = (AccountDetails) auth.getPrincipal();
        accountService.updateAccount(updateInfo, message, accountDetails);
        return "redirect:/users/" + accountDetails.accountId();
    }

    @DeleteMapping("/{accountId}/delete")
    public String deleteAccount(@PathVariable Long accountId) {
        accountService.delete(accountId);
        return "redirect:/";
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
