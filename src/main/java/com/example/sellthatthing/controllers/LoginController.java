package com.example.sellthatthing.controllers;

import com.example.sellthatthing.models.AccountDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/login")
public class LoginController {
    @GetMapping
    public String loadLoginPage(HttpServletRequest request) {
        if (request.getUserPrincipal() == null){
            return "login";
        }
        return "redirect:/";
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
