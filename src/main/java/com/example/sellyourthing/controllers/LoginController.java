package com.example.sellyourthing.controllers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/***
 * @author Ife Sunmola
 * Login Controller class. Mainly used to show error messages on the login page
 */
@Controller
@AllArgsConstructor
@RequestMapping("/login")
@SessionAttributes("message")
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public String loadLoginPage(HttpServletRequest request) {
        if (request.getUserPrincipal() == null) {// user is not logged in, show log in page
            return "login";
        }
        return "redirect:/";// user is logged in, show home page
    }

    @GetMapping("/logout")
    public String logout(@ModelAttribute("message") HashMap<String, String> message) {
        // on logout, redirect back to login page with a  success message
        message.clear();
        message.put("logoutSuccess", "You have been logged out");
        return "redirect:login";
    }

    @GetMapping("/login-error")
    public String login(HttpServletRequest request, @ModelAttribute("message") HashMap<String, String> message) {
        // login failed, redirect back to login page with an error message
        HttpSession session = request.getSession();
        String errorCause = ((AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).getMessage(); // get the cause of login failure

        String errorMessage = switch (errorCause) {
            case "Bad credentials" -> "Invalid email or password";
            case "User is disabled" ->
                    "Your account has not been verified yet. Check your email for a verification code. Verify your account <a href='/register/verify-account'> here </a>";
            case "User account is locked" -> "Your account has been locked. Contact an admin";
            default -> "An error occurred, login again";
        };
        message.clear();
        message.put("loginFailed", errorMessage);
        return "redirect:/login";
    }
}
