package com.example.sellthatthing.models;

import com.example.sellthatthing.datatransferobjects.NewAccountRequest;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Entity
@Data // toString, equalsAndHashCode, Getter, Setter, RequiredArgsConstructor
@RequiredArgsConstructor // for some reason, seed data won't work if I don't add this, again
@NoArgsConstructor
public class Account implements UserDetails {
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

    @OneToMany(mappedBy = "posterAccount", fetch = FetchType.EAGER)
    private List<Post> posts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public Account(final NewAccountRequest newAccountRequest) {
        firstName = newAccountRequest.getFirstName();
        lastName = newAccountRequest.getLastName();
        email = newAccountRequest.getEmail();
        dateOfBirth = newAccountRequest.getDateOfBirth();
        role = newAccountRequest.getEmail().toLowerCase(Locale.ROOT).contains("+admin") ? "ADMIN" : "USER";
        password = newAccountRequest.getPassword();
    }
}
