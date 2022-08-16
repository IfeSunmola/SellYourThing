package com.example.sellthatthing.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateAccountRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    // password will be handled with spring security
}
