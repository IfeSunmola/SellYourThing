package com.example.sellthatthing.services;

import com.example.sellthatthing.controllers.CustomErrorController;
import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.models.AccountDetails;
import com.example.sellthatthing.models.Category;
import com.example.sellthatthing.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public void delete(String categoryName, HashMap<String, String> message, HttpServletRequest request) {
        message.clear();
        if (categoryRepository.existsByCategoryNameIgnoreCase(categoryName)) {
            message.put("deleteCategoryStatus", "true");
            message.put("deleteCategoryMessage", "Category <strong>" + categoryName + "</strong> has been deleted");
            logger.warn("Category: " + categoryName + " has been deleted by " + getAccountInfo(request));
            categoryRepository.deleteByCategoryNameIgnoreCase(categoryName);
        }
        else {
            message.put("deleteCategoryStatus", "false");
            message.put("deleteCategoryMessage", "Category <strong>" + categoryName + "</strong> was not found");
        }
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Category id: '" + id + "' was not found"));
    }

    @Transactional
    public void edit(String oldName, String newName, HashMap<String, String> message, HttpServletRequest request) {
        newName = StringUtils.capitalize(newName.toLowerCase(Locale.ROOT));
        message.clear();
        if (categoryRepository.existsByCategoryNameIgnoreCase(newName)) {
            message.put("editCategoryStatus", "false");
            message.put("editCategoryMessage", "Category <strong>'" + newName + "'</strong> already exists. Edit failed");
            return;
        }
        if (oldName.equalsIgnoreCase(newName)) {
            message.put("editCategoryStatus", "false");
            message.put("editCategoryMessage", "New name must be different from old name. Edit failed");
            return;
        }
        categoryRepository.editName(oldName, newName);
        logger.info("Category: " + oldName + " was edited to " + newName + " by " + getAccountInfo(request));
        message.put("editCategoryStatus", "true");
        message.put("editCategoryMessage", "<strong>" + oldName + "</strong> has been updated to <strong>" + newName + "</strong>");
    }

    public Category findByName(String categoryName) {
        return categoryRepository.findByCategoryNameIgnoreCase(categoryName).orElseThrow(()
                -> new ResourceNotFoundException("Category : '" + categoryName + "' was not found"));
    }

    @Transactional
    public void save(String newCategoryName, HashMap<String, String> message, HttpServletRequest request) {
        newCategoryName = StringUtils.capitalize(newCategoryName.toLowerCase(Locale.ROOT));
        message.clear();
        if (categoryRepository.existsByCategoryNameIgnoreCase(newCategoryName)) {
            message.put("addCategoryStatus", "false");
            message.put("addCategoryMessage", "Category <strong>" + newCategoryName + "</strong> already exists");
            return;
        }
        categoryRepository.save(new Category(newCategoryName, LocalDateTime.now()));
        logger.info("A new Category was added by: " + getAccountInfo(request));
        message.put("addCategoryStatus", "true");
        message.put("addCategoryMessage", "<strong>" + newCategoryName + "</strong> has been added");
    }

    private String getAccountInfo(HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        AccountDetails authAccount = (AccountDetails) auth.getPrincipal();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' h:m a"));
        return authAccount.firstName() + " " + authAccount.lastName() + " (" + authAccount.accountId() + ") at " + time;
    }
}
