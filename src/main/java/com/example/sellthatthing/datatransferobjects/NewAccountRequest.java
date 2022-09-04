package com.example.sellthatthing.datatransferobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class NewAccountRequest {
    @NotNull(message = "First name can't be empty")
    @Size(min = 3, max = 15, message = "First name should be between 3 and 15 characters")
    private String firstName;

    @NotNull(message = "Last name can't be empty")
    @Size(min = 3, max = 15, message = "Last name should be between 3 and 15 characters")
    private String lastName;

    @NotNull(message = "Email can't be empty")
    @Email(message = "Enter a valid email")
    private String email;

    @NotNull(message = "Date of birth is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // for thymeleaf
    private LocalDate dateOfBirth; // age will be checked in service class

    @NotNull(message = "Password can't be empty")
    @Size(min = 7, message = "7 or more characters required")
    private String password;

    @NotNull(message = "Confirm Password can't be empty")
    @Size(min = 7, message = "7 or more characters required") // passwords will be checked in service class
    private String confirmPassword;
}
