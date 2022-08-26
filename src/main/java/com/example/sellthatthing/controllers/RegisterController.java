package com.example.sellthatthing.controllers;

import com.example.sellthatthing.datatransferobjects.NewAccountRequest;
import com.example.sellthatthing.services.AccountService;
import com.example.sellthatthing.services.VerificationCodeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final AccountService accountService;
    private final VerificationCodeService codeService;

    @GetMapping
    public String loadRegisterPage(Model model) {
        model.addAttribute("registrationDto", new NewAccountRequest());
        return "register";
    }

    @PostMapping
    public String processRegisterForm(@ModelAttribute NewAccountRequest newAccountRequest, RedirectAttributes redirectAttributes) {
        Long verifyCodeId = accountService.createAccount(newAccountRequest);
        redirectAttributes.addFlashAttribute("verifyCodeId", verifyCodeId);
        return "redirect:/register/verify-account"; // redirect user to login page after registration
    }

    @GetMapping("/verify-account")
    public String loadVerificationPage(@ModelAttribute("verifyCodeId") String verifyCodeId, Model model) {
        // I didn't need to add a model to the get mapping. The flash attribute can directly be used from thymeleaf but intellij likes being a bitch
        // and complains about everything
        model.addAttribute("verifyCodeId", verifyCodeId);
        return "verify-account";
    }

    /*
     * There's a bit of beautiful spaghetti code in the way verify-account is handled.
     * In processRegisterForm, a flash attribute that contains the verification code id was added. I couldn't find a way to get that flash attribute
     * directly in confirmVerifyCode below. So what was my genius plan? Send the verification to the view (thymeleaf), store it in a hidden input tag
     * and then send it back with the post request.
     *
     * Could that lead to some security risks? Probably. But if you think about it, we're just a tiny dot in this big universe so does it
     * really matter?
     */
    @PostMapping("/verify-account")
    public String confirmVerifyCode(@RequestParam String enteredVerificationCode, @RequestParam String verificationCodeId) {
        System.out.println("Entered Code: " + enteredVerificationCode);
        System.out.println("Code in Db: " + codeService.findByCodeId(Long.valueOf(verificationCodeId)).getCode());
        accountService.confirmVerificationCode(enteredVerificationCode, verificationCodeId);
        return "verify-account";
    }
}
