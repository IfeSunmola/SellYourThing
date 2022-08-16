package com.example.sellthatthing.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) private Long categoryId;
    @NonNull private String categoryName;
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "postCategory")
    @JsonManagedReference(value = "postsCategory")
    private Set<Post> posts;
}
