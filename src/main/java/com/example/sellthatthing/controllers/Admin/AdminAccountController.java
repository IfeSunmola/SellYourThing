package com.example.sellthatthing.controllers.Admin;

import com.example.sellthatthing.models.Account;
import com.example.sellthatthing.models.AccountDetails;
import com.example.sellthatthing.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/*
 * For everything relating to the admin managing accounts
 * */
@Controller
@AllArgsConstructor
@RequestMapping("/admin/accounts")
@SessionAttributes("message")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminAccountController {
    private final AccountService accountService;

    /*
     * Get the currently logged-in user
     * */
    private AccountDetails getAuthAccount(HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        return (AccountDetails) auth.getPrincipal();
    }

    /*
     * loads the page that shows all accounts
     * */
    @GetMapping
    public String viewAllAccounts(Model model, HttpServletRequest request, @ModelAttribute("message") HashMap<String, String> message) {
        model.addAttribute("allAccounts", accountService.findAll());

        AccountDetails authAccount = getAuthAccount(request);
        model.addAttribute("authAccountId", authAccount.accountId().toString()); // toString for javascript
        model.addAttribute("authAccountEmail", authAccount.email());
        return "admin/admin-view-account";
    }


    /*
     * Find an account by the account id and return a response body, i.e. JSON
     * The response body is used in jquery to prefill the 'disable' account and 'delete' account modals
     * */
    @GetMapping("/find/{id}")
    @ResponseBody
    public HashMap<String, String> findById(@PathVariable Long id) {
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

    /*
     * Disable an account by id
     * */
    @PatchMapping("/disable/{accountId}")
    public String disable(@PathVariable Long accountId, @ModelAttribute("message") HashMap<String, String> message) {
        accountService.disableById(accountId, message); // log the user out too
        return "redirect:/admin/accounts";
    }

    /*
     * Enable an account by id
     * */
    @PatchMapping("/enable/{accountId}")
    public String enable(@PathVariable Long accountId, @ModelAttribute("message") HashMap<String, String> message) {
        accountService.enableById(accountId, message);
        return "redirect:/admin/accounts";
    }

    /*
     * Delete an account by id
     * */
    @DeleteMapping("/delete/{accountId}")
    public String deleteAccount(@PathVariable Long accountId, @ModelAttribute("message") HashMap<String, String> message) {
        accountService.deleteById(accountId, message);
        return "redirect:/admin/accounts";
    }
}
