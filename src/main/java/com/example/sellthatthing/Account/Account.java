package com.example.sellthatthing.Account;

import com.example.sellthatthing.Post.Post;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class Account implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long accountId;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String email;
    @NonNull private LocalDate dateOfBirth;
    @NonNull private String role;
    @NonNull private String password;
    @NonNull private boolean enabled = false;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    @JsonManagedReference(value = "posterAccount")
    @OneToMany(mappedBy = "posterAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Post> posts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
