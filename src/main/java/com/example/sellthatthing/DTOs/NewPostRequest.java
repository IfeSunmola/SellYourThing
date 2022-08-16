package com.example.sellthatthing.DTOs;

import lombok.Getter;

@Getter
public class NewPostRequest {
    private String title;
    private String body;
    private String categoryName;
    private Long posterAccountId;
}
