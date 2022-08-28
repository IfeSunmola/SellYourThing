package com.example.sellthatthing.controllers;

import com.example.sellthatthing.datatransferobjects.NewAccountRequest;
import com.example.sellthatthing.datatransferobjects.VerificationDto;
import com.example.sellthatthing.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final AccountService accountService;

    @GetMapping
    public String loadRegisterPage(Model model) {
        model.addAttribute("registrationDto", new NewAccountRequest());
        return "register";
    }

    @PostMapping
    public String processRegisterForm(@ModelAttribute NewAccountRequest newAccountRequest, HttpSession session) {
        VerificationDto verificationDto = accountService.createAccount(newAccountRequest);
        session.setAttribute("verificationDto", verificationDto);
        return "redirect:/register/verify-account";
    }

    @GetMapping("/verify-account")
    public String loadVerificationPage() {
        return "verify-account";
    }

    @PostMapping("/verify-account")
    public String confirmVerifyCode(@RequestParam String enteredVerificationCode, HttpServletRequest request, HttpSession session) {
        VerificationDto verificationDto = (VerificationDto) session.getAttribute("verificationDto");
        session.removeAttribute("verifyInfo");

        Long codeId = verificationDto.codeId();
        Long accountId = verificationDto.accountId();
        String rawPassword = verificationDto.rawPassword();

        accountService.confirmVerificationCode(enteredVerificationCode, codeId, accountId);
        accountService.doManualLogin(accountId, rawPassword, request);
        return "redirect:/?loginSuccess";
    }
}
