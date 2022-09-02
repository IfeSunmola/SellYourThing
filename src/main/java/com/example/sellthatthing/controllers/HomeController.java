package com.example.sellthatthing.controllers;

import com.example.sellthatthing.datatransferobjects.PostsSortDto;
import com.example.sellthatthing.services.CategoryService;
import com.example.sellthatthing.services.CityService;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.HashMap;

@Controller
@AllArgsConstructor
@SessionAttributes("message")
public class HomeController {
    private final PostService postService;
    private final CityService cityService;
    private final CategoryService categoryService;

    @GetMapping
    public String showIndexPage(final Model model, PostsSortDto postsSortDto,
                                @ModelAttribute("message") HashMap<String, String> message) {
        String cityName = postsSortDto.getCity();
        String categoryName = postsSortDto.getCategory();
        String order = postsSortDto.getOrder();
        String searchText = postsSortDto.getSearchText();

        model.addAttribute("posts", postService.findAllWithSorting(cityName, categoryName, order, searchText));
        model.addAttribute("cities", cityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }
}
