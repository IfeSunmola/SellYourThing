package com.example.sellthatthing.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewAccountRequest {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String password;
    private String confirmPassword;
}
