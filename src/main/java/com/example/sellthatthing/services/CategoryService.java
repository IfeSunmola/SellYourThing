package com.example.sellthatthing.services;

import com.example.sellthatthing.exceptions.ResourceAlreadyExistsException;
import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.models.Category;
import com.example.sellthatthing.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category saveNewCategory(Category newCategory) {
        // below if statement is needed so the seed data can work as expected.
        if (newCategory.getDateCreated() == null) {
            newCategory.setDateCreated(LocalDateTime.now());
        }

        // category name already exists
        String newCategoryName = newCategory.getCategoryName();
        if (categoryRepository.existsByCategoryName(newCategoryName)) {
            throw new ResourceAlreadyExistsException("Category '" + newCategoryName + "' already exists");
        }
        return categoryRepository.save(newCategory);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public void delete(String categoryName, HashMap<String, String> message) {
        message.clear();
        if (categoryRepository.existsByCategoryName(categoryName)) {
            message.put("deleteCategoryStatus", "true");
            message.put("deleteCategoryMessage", "Category <strong>" + categoryName + "</strong> has been deleted");
            categoryRepository.deleteByCategoryName(categoryName);
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
    public void edit(String oldName, String newName, HashMap<String, String> message) {
        message.clear();
        if (categoryRepository.existsByCategoryName(newName)) {
            message.put("editCategoryStatus", "false");
            message.put("editCategoryMessage", "Category <strong>'" + newName + "'</strong> already exists. Edit failed");
            return;
        }
        if (oldName.equalsIgnoreCase(newName)) {
            message.put("editCategoryStatus", "false");
            message.put("editCategoryMessage", "New name must be different from old name. Edit failed");
            return;
        }
        categoryRepository.updateName(oldName, newName);
        message.put("editCategoryStatus", "true");
        message.put("editCategoryMessage", "<strong>" + oldName + "</strong> has been updated to <strong>" + newName + "</strong>");
    }

    public Category findByName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName).orElseThrow(()
                -> new ResourceNotFoundException("Category : '" + categoryName + "' was not found"));
    }
}
