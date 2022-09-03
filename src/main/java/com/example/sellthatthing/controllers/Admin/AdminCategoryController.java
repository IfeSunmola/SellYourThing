package com.example.sellthatthing.controllers.Admin;

import com.example.sellthatthing.controllers.CustomErrorController;
import com.example.sellthatthing.models.AccountDetails;
import com.example.sellthatthing.services.AccountService;
import com.example.sellthatthing.services.CategoryService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/*
 * For everything relating to the admin managing categories
 * */
@Controller
@AllArgsConstructor
@RequestMapping("/admin/categories")
@SessionAttributes("message")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final Logger logger = LoggerFactory.getLogger(AdminCategoryController.class);

    /*
     * Get the currently logged-in user
     * */
    private AccountDetails getAuthAccount(HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        return (AccountDetails) auth.getPrincipal();
    }

    /*
     * Method to check if the admin id that was passed from client is the same as the admin id that is logged in.
     * Returns true if it's the same, and false if not.
     * */
    private boolean hasValidAccess(Long authId, HashMap<String, String> message, HttpServletRequest request) {
        Long currentAccountId = getAuthAccount(request).accountId();
        message.clear();
        if (!authId.equals(currentAccountId)) {
            accountService.doManualLogout(request);
            logger.error("Account id: " + currentAccountId + " sent a request as account id: " + authId);
            message.put("forcedLogout", "false");
            message.put("forcedLogoutMessage", "An error occurred, you have been logged out");
            return false;
        }
        return true;
    }

    /*
     * loads the page that shows all posts
     * */
    @GetMapping
    public String getCategories(Model model, HttpServletRequest request, @ModelAttribute("message") HashMap<String, String> message) {
        model.addAttribute("allCategories", categoryService.findAll());
        AccountDetails authAccount = getAuthAccount(request);
        model.addAttribute("authAccountId", authAccount.accountId().toString()); // toString for javascript
        model.addAttribute("authAccountEmail", authAccount.email());
        return "admin/admin-view-categories";
    }

    /*
     * Edit a category by its name (id)
     * */
    @PatchMapping("/edit/{oldName}/{newName}")
    public String edit(@PathVariable String oldName, @PathVariable String newName, @ModelAttribute("message") HashMap<String, String> message) {
        categoryService.edit(oldName, newName, message);
        return "redirect:/admin/categories";
    }

    /*
     * Delete a category
     * */
    @DeleteMapping("/delete/{authId}/{categoryName}")
    public String delete(@PathVariable Long authId, @PathVariable String categoryName,
                         @ModelAttribute("message") HashMap<String, String> message,
                         HttpServletRequest request) {
        if (!hasValidAccess(authId, message, request)) {
            return "/login";
        }
        System.out.println("Deleting: " + categoryName);
        categoryService.delete(categoryName, message);
        return "redirect:/admin/categories";
    }
}
