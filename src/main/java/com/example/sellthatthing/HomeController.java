package com.example.sellthatthing;

import com.example.sellthatthing.Post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    private final PostService postService;

    @GetMapping
    public String showIndexPage(final Model model) {
        model.addAttribute("posts", postService.findAll());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) { // user is authenticated
            System.out.print("");
        }
        return "index";
    }

    @GetMapping("/css/style.css")
    public String temp() {
        return "redirect:/";
    }
}
