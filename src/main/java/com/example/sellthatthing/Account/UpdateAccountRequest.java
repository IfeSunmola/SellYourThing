package com.example.sellthatthing.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UpdateAccountRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private LocalDate dateOfBirth;
    // password will be handled with spring security
}
