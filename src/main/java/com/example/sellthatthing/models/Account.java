package com.example.sellthatthing.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long accountId;
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String email;
    @NonNull private LocalDate dateOfBirth;
    @NonNull private String password;

    @JsonManagedReference(value = "posterAccount")
    @OneToMany(mappedBy = "posterAccount", cascade = CascadeType.ALL)
    private List<Post> posts;
}
