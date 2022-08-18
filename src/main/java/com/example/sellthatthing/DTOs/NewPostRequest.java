package com.example.sellthatthing.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewPostRequest {
    private String title;
    private String body;
    private String categoryName;
    private BigDecimal price;
    private String imageUrl;
    private String location;
    private Long posterAccountId;
}
