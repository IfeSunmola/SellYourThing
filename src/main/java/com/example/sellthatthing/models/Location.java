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
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) private Long locationId;
    @NonNull private String locationName;
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "postLocation")
    @JsonManagedReference(value = "postsLocation")
    private Set<Post> posts;
}
