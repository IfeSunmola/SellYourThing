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
    @Id @NonNull private String cityName;
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "postCity")
    private Set<Post> posts;
}
