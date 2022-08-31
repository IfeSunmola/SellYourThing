package com.example.sellthatthing.services;

import com.example.sellthatthing.models.Category;
import com.example.sellthatthing.repositories.CategoryRepository;
import com.example.sellthatthing.exceptions.ResourceAlreadyExistsException;
import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

//    public Category update(UpdateCategoryRequest updateInfo) {
//        String oldCategoryName = updateInfo.getOldCategoryName();
//        String newCategoryName = updateInfo.getNewCategoryName();
//
//        // new category name already exists
//        if (categoryRepository.existsByCategoryName(newCategoryName)) {
//            throw new ResourceAlreadyExistsException("Category '" + newCategoryName + "' already exists");
//        }
//
//        // old category name was not found
//        Category categoryToUpdate = categoryRepository.findByCategoryName(oldCategoryName).orElseThrow(()
//                -> new ResourceNotFoundException("Category: " + oldCategoryName + " was not found"));
//
//        // everything is good.
//        categoryToUpdate.setCategoryName(newCategoryName);
//        categoryToUpdate.setDateUpdated(LocalDateTime.now());
//        return categoryRepository.save(categoryToUpdate);
//    }

    public void delete(String categoryName) {
        categoryRepository.deleteById(findByCategoryName(categoryName).getCategoryName());
    }

    public Category findByCategoryName(String categoryName) {
        return categoryRepository.findById(categoryName).orElseThrow(()
                -> new ResourceNotFoundException("Category '" + categoryName + "' was not found"));
    }
}
