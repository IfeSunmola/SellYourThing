package com.example.sellthatthing.datatransferobjects;

import lombok.*;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter // No setter == null from thymeleaf.
public class NewPostRequest {
    private String title;
    private String body;
    private BigDecimal price;
    private String imageUrl;
    private String categoryName;
    private String cityName;
    private Long posterAccountId;
}
