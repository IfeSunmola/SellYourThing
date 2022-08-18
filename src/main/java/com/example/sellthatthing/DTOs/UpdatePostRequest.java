package com.example.sellthatthing.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class UpdatePostRequest {
    private final String title;
    private final String body;
    private final String categoryName;
    private final BigDecimal price;
    private final String imageUrl;
}
