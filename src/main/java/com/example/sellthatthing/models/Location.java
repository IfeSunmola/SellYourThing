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
public class Location {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long locationId;
    @NonNull private String locationName;
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "postLocation")
    private Set<Post> posts;
}
