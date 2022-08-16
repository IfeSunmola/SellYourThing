package com.example.sellthatthing.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewAccountRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
