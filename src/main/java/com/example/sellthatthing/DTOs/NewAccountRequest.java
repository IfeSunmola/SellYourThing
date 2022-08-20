package com.example.sellthatthing.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class NewAccountRequest {
    private String firstName;
    private String lastName;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // for thymeleaf
    private LocalDate dateOfBirth;
    private String password;
    private String confirmPassword;
}
