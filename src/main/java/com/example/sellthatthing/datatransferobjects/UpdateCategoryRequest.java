package com.example.sellthatthing.datatransferobjects;

import lombok.Getter;

@Getter
public class UpdateCategoryRequest {
    private String oldCategoryName;
    private String newCategoryName;
}
