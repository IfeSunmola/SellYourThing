package com.example.sellyourthing.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @NonNull
    @Column(unique = true)
    private String name;

    @NonNull private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "postCategory", cascade = CascadeType.ALL)
    private Set<Post> posts;
}
