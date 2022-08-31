package com.example.sellthatthing.datatransferobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class UpdateAccountRequest {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    // password will be handled with spring security
}
