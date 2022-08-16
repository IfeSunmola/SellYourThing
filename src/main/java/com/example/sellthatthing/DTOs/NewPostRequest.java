package com.example.sellthatthing.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewPostRequest {
    private String title;
    private String body;
    private String categoryName;
    private Long posterAccountId;
}
