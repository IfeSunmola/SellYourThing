package com.example.sellthatthing.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/***
 * @author Ife Sunmola
 * Login Controller class
 */
@Controller
@AllArgsConstructor
@RequestMapping("/login")
@SessionAttributes("message")
public class LoginController {
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
    public String login(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
//        model.addAttribute("errorMessage", errorMessage);
        redirectAttributes.addFlashAttribute("errorMessage", true);
        return "redirect:/login";
    }
}
