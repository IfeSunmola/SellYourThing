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
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

    @NonNull
    @Column(unique = true)
    private String categoryName;

    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "postCategory")
    private Set<Post> posts;
}
