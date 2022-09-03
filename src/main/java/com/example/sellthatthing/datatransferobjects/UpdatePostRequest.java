package com.example.sellthatthing.datatransferobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class UpdatePostRequest {
    private final String title;
    private final String body;
    private final Long categoryId;
    private final BigDecimal price;
    private final String imageUrl;
    private final String city;
}
