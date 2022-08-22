package com.example.sellthatthing.datatransferobjects;

import lombok.*;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter // No setter == null
public class NewPostRequest {
    private String title;
    private String body;
    private Long categoryId;
    private BigDecimal price;
    private String imageUrl;
    private String location;
    private Long posterAccountId;
}
