package com.example.sellthatthing.controllers;

import com.example.sellthatthing.datatransferobjects.PostsSortDto;
import com.example.sellthatthing.services.CategoryService;
import com.example.sellthatthing.services.CityService;
import com.example.sellthatthing.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomeController {
    private final PostService postService;
    private final CityService cityService;
    private final CategoryService categoryService;

    @GetMapping
    public String showIndexPage(final Model model, PostsSortDto postsSortDto) {
        Long cityId = postsSortDto.getCityId();
        Long categoryId = postsSortDto.getCategoryId();
        String order = postsSortDto.getOrder();
        String searchText = postsSortDto.getSearchText();

        model.addAttribute("posts", postService.findAllWithSorting(cityId, categoryId, order, searchText));
        model.addAttribute("cities", cityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "index";
    }
}
