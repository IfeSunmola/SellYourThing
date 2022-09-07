package com.example.sellyourthing.controllers;

import com.example.sellyourthing.datatransferobjects.NewAccountRequest;
import com.example.sellyourthing.datatransferobjects.VerificationDto;
import com.example.sellyourthing.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;

/**
 * @author Ife Sunmola
 * <p>
 * Registration flow:
 * <p>1. Register form is loaded. If an already logged-in user tries to access the register form, they will be redirected to the home page.</p>
 * <p>2. When the user submits the form, it is sent to the PostMapping for validation. AccountService is called to check the form data for errors.
 * If there are errors, it will be added to the BindingResult.</p>
 * <p>3. Back in controller, if there are any errors, we go back to the same register page and thymeleaf will show the errors.</p>
 * <p>4. Account Service is called once again to create an account. The account is created and a code is sent. The method returns a 'VerificationDto'
 * that contains the ID of the verification code, the account ID and the users' password. They will be used to log in the user after creating an account.</p>
 * <p>5. The 'VerificationDto' is added as a session attribute, so it will be gotten in the verify account page.</p>
 * ---------------------------------------------------------------------------------------------------------------------------------------------------
 * <p>6. When the verify account page is loaded, the model attribute is also added so error messages can be show</p>
 * <p>7. When a code is submitted, the PostMapping /verify-account is called.</p>
 * <p>8. The verification DTO is gotten from the session and deleted.</p>
 * <p>9. A call to account service is made to confirm the verification code. The method returns views based on what problem was encountered</p>
 * <p>- If the verification code has expired, it redirects back to the register page for the user to re register. Their account would have been deleted.</p>
 * <p>- If the verification code is not the same, the verify-page is shown again along with an error message</p>
 * <p>10. If everything is fine, the account will be enabled and the verification code will be deleted from the database.
 * "success" will be returned</p>
 * <p>11. Back in controller, if 'success' is returned, the account has been enabled and the user can now be automatically logged in. The AccountService
 * class is called again to log the user in. After being logged in, they will be redirected to the home page and a success message will be show.</p>
 * <p>12. If 'success' was not returned, whatever was returned will be the view.</p>
 */
@Controller
@AllArgsConstructor
@RequestMapping("/register")
@SessionAttributes("message")

public class RegisterController {
    private final AccountService accountService;

    @GetMapping
    public String loadRegisterPage(Model model, HttpServletRequest request, @ModelAttribute("message") HashMap<String, String> message) {
        if (request.getUserPrincipal() == null) {// user is not logged in, show register page
            model.addAttribute("newAccountRequest", new NewAccountRequest());
            return "register";
        }
        return "redirect:/";// user is logged in, go home
    }

    @PostMapping
    public String processRegisterForm(@ModelAttribute("newAccountRequest") @Valid NewAccountRequest newAccountRequest,
                                      BindingResult errors, HttpSession session) {
        accountService.checkForErrors(newAccountRequest, errors);// validate form
        if (errors.hasErrors()) {// if any errors, show register form again, with error messages
            return "register";
        }
        VerificationDto verificationDto = accountService.createAccount(newAccountRequest);// create the account
        session.setAttribute("verificationDto", verificationDto); // add info needed for account verification
        return "redirect:/register/verify-account";
    }

    @GetMapping("/verify-account")
    public String loadVerificationPage(@ModelAttribute("message") HashMap<String, String> message) {
        return "verify-account";
    }

    @PostMapping("/verify-account")
    public String confirmVerifyCode(@RequestParam String enteredVerificationCode, @ModelAttribute("message") HashMap<String, String> message, HttpServletRequest request) {
        VerificationDto verificationDto = (VerificationDto) request.getSession().getAttribute("verificationDto"); // get info from the session
        request.getSession().removeAttribute("verifyInfo");
        message.clear();

        Long codeId = verificationDto.codeId();
        Long accountId = verificationDto.accountId();
        String rawPassword = verificationDto.rawPassword();

        String verificationStatus = accountService.confirmVerificationCode(enteredVerificationCode, codeId, accountId, message);

        if (verificationStatus.equals("success")) {
            // for some reason, if I try to do the manual login in account service class, the account won't get enabled... till after a login
            // attempt has been made
            accountService.doManualLogin(accountId, rawPassword, request);
            message.put("registrationSuccess", "Registration successful. You have been logged in.");
            return "redirect:/";
        }
        return verificationStatus;
    }
}
