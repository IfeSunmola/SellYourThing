package com.example.sellthatthing.models;

import com.example.sellthatthing.datatransferobjects.NewAccountRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Entity
@Data // toString, equalsAndHashCode, Getter, Setter, RequiredArgsConstructor
@RequiredArgsConstructor // for some reason, seed data won't work if I don't add this, again
@NoArgsConstructor
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long accountId;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String email;
    @NonNull private LocalDate dateOfBirth;
    @NonNull private String role;
    @NonNull private String password;
    @NonNull private boolean enabled;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    @OneToMany(mappedBy = "accountId", fetch = FetchType.EAGER)
    private List<Post> posts;

    public Account(final NewAccountRequest newAccountRequest) {
        firstName = newAccountRequest.getFirstName();
        lastName = newAccountRequest.getLastName();
        email = newAccountRequest.getEmail();
        dateOfBirth = newAccountRequest.getDateOfBirth();
        role = newAccountRequest.getEmail().toLowerCase(Locale.ROOT).contains("+admin") ? "ADMIN" : "USER";
        password = newAccountRequest.getPassword();
    }
}
