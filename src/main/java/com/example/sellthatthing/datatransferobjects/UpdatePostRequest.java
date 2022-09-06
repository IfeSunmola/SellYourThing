package com.example.sellthatthing.datatransferobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class UpdatePostRequest {
    private Long accountId;
    private String title;
    private String body;
    private String categoryName;
    private BigDecimal price;
    private String cityName;
    private MultipartFile image;
}
