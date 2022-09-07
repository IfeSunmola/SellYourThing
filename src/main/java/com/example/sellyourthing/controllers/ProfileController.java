package com.example.sellyourthing.controllers;

import com.example.sellyourthing.datatransferobjects.PostsSortDto;
import com.example.sellyourthing.datatransferobjects.UpdateAccountRequest;
import com.example.sellyourthing.models.Account;
import com.example.sellyourthing.models.AccountDetails;
import com.example.sellyourthing.services.AccountService;
import com.example.sellyourthing.services.CategoryService;
import com.example.sellyourthing.services.CityService;
import com.example.sellyourthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author Ife Sunmola
 * Profile controller for everything relating to a users profile
 */
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
        AccountDetails authAccount; // logged in user
        boolean isAuthenticated = !(auth instanceof AnonymousAuthenticationToken);

        model.addAttribute("currentAccount", currentAccount);
        if (isAuthenticated) {// person viewing the page is authenticated
            authAccount = (AccountDetails) auth.getPrincipal();
            isSameUser = authAccount.accountId().equals(currentAccount.getAccountId());// is the person viewing the page the owner of the page?

            model.addAttribute("authAccount", authAccount);
            model.addAttribute("isSameUser", isSameUser);

            if (isSameUser) {// used to show the update account modal
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
    public String updateAccount(@ModelAttribute("updateAccountDto") @Valid UpdateAccountRequest updateInfo, BindingResult errors,
                                @ModelAttribute("message") HashMap<String, String> message) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((auth instanceof AnonymousAuthenticationToken)) { // not authenticated
            return "/login";
        }
        message.clear();
        AccountDetails accountDetails = (AccountDetails) auth.getPrincipal();
        accountService.checkForErrors(updateInfo, errors, accountDetails);
        if (errors.hasErrors()) {// there were errors in the form
            StringBuilder errorMessage = new StringBuilder("<strong>Update Failed</strong>:");

            for (ObjectError error : errors.getAllErrors()) {
                errorMessage.append("<li>").append(error.getDefaultMessage()).append("</li>");
            }
            message.put("updateFailed", errorMessage.toString());
            return "redirect:/users/" + accountDetails.accountId();
        }
        accountService.updateAccount(updateInfo, accountDetails);
        message.put("updateSuccess", "Your account has been updated");
        return "redirect:/users/" + accountDetails.accountId();
    }

    @GetMapping("/delete-account")
    public String getDeleteAccount() {
        return redirectToHome();
    }

    @DeleteMapping("/delete-account")
    public RedirectView deleteAccount(@RequestParam String confirmEmail, @ModelAttribute("message") HashMap<String, String> message,
                                      HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        if ((auth instanceof AnonymousAuthenticationToken)) { // not authenticated
            return new RedirectView("/login");
        }
        AccountDetails accountDetails = (AccountDetails) auth.getPrincipal();

        return accountService.delete(confirmEmail, accountDetails, request, message);
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
