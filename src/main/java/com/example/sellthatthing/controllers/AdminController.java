package com.example.sellthatthing.controllers;

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

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
@SessionAttributes("message")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final AccountService accountService;

    private AccountDetails getAuthAccount(HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        return (AccountDetails) auth.getPrincipal();
    }

    @GetMapping("/findId/{id}")
    @ResponseBody
    public HashMap<String, String> getById(@PathVariable Long id) {
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
    public String viewAllAccounts(Model model, HttpServletRequest request, @ModelAttribute("message") HashMap<String, Boolean> message) {
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
}
