package com.example.sellthatthing.controllers;

import com.example.sellthatthing.datatransferobjects.NewAccountRequest;
import com.example.sellthatthing.datatransferobjects.VerificationDto;
import com.example.sellthatthing.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final AccountService accountService;

    @GetMapping
    public String loadRegisterPage(Model model, HttpServletRequest request) {
        if (request.getUserPrincipal() == null) {
            model.addAttribute("newAccountRequest", new NewAccountRequest());
            return "register";
        }
        return "redirect:/";
    }

    @PostMapping
    public String processRegisterForm(@ModelAttribute("newAccountRequest") @Valid NewAccountRequest newAccountRequest,
                                      BindingResult errors, HttpSession session) {
        accountService.checkForErrors(newAccountRequest, errors);
        if (errors.hasErrors()) {
            return "register";
        }
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
