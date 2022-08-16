package com.example.sellthatthing.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdatePostRequest {
    private final String title;
    private final String body;
    private final String categoryName;
}
