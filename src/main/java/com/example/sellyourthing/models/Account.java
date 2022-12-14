package com.example.sellyourthing.models;

import com.example.sellyourthing.datatransferobjects.NewAccountRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @NonNull @DateTimeFormat(pattern = "yyyy-MM-dd")private LocalDate dateOfBirth;
    @NonNull private String role;
    @NonNull private String password;
    @NonNull private boolean enabled;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

//    @OneToMany(mappedBy = "posterAccount", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    private List<Post> posts;

    @OneToMany(mappedBy = "posterAccount", cascade = CascadeType.ALL)
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
