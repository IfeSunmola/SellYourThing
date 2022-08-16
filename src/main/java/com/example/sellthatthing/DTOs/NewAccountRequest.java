package com.example.sellthatthing.DTOs;

import lombok.Getter;

@Getter
public class NewAccountRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
