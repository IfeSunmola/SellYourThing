package com.example.sellthatthing.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewPostRequest {
    private String title;
    private String body;
    private String categoryName;
    private Long posterAccountId;
}
