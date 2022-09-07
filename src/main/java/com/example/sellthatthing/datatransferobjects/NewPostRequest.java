package com.example.sellthatthing.datatransferobjects;

import com.example.sellthatthing.custom_annotations.ValidImageFile;
import com.example.sellthatthing.custom_annotations.ValidSelectOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter // No setter == null from thymeleaf.
public class NewPostRequest {
    @NotNull(message = "Title is required")
    @Size(min = 20, message = "Title must be longer than 20 characters ")
    @Size(max = 100, message = "100 characters maximum")
    private String title;

    @NotNull(message = "Body is required")
    @Size(min = 100, message = "100 or more characters required")
    @Size(max = 3000, message = "3000 characters is maximum")
    private String body;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "1.0", message = "Enter a valid price")
    private BigDecimal price;

    @ValidImageFile
    private MultipartFile image;

    @NotNull(message = "Select a category")
    @ValidSelectOption(message = "Category is required")
    private String categoryName;

    @NotNull(message = "Select a city")
    @ValidSelectOption(message = "City is required")
    private String cityName;

    private Long posterAccountId;
}
