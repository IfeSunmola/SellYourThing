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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long cityId;
    @NonNull private String cityName;
    private LocalDateTime dateCreated;

    @OneToMany(mappedBy = "cityId")
    private Set<Post> posts;
}
