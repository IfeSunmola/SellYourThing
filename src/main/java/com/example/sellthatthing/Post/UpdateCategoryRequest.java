package com.example.sellthatthing.Post;

import lombok.Getter;

@Getter
public class UpdateCategoryRequest {
    private String oldCategoryName;
    private String newCategoryName;
}
