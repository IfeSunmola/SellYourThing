package com.example.sellyourthing.datatransferobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class UpdateAccountRequest {
    @NotNull(message = "First name is required")
    @Size(min = 3, max = 15, message = "First name should be between 3 and 15 characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min = 3, max = 15, message = "Last name should be between 3 and 15 characters")
    private String lastName;

    @Size(min = 3, message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotNull(message = "Date of birth is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // for thymeleaf
    private LocalDate dateOfBirth;
    // password will be handled with spring security
}
