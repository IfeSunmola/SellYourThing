package com.example.sellthatthing.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class City {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @NonNull private String name;

    @NonNull private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "postCity", cascade = CascadeType.ALL)
    private Set<Post> posts;
}
