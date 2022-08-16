package com.example.sellthatthing.DTOs;

import lombok.Getter;

@Getter
public class UpdateCategoryRequest {
    private String oldCategoryName;
    private String newCategoryName;
}
