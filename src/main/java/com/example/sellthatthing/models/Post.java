package com.example.sellthatthing.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long postId;
    @NonNull private String title;
    @NonNull @Column(length = 3000)private String body;
    @NonNull private LocalDateTime createdAt;
    @NonNull private BigDecimal price;
    @NonNull private String imageUrl;
    @NonNull private String location;

    @JsonBackReference(value = "postsCategory")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    @NonNull
    private Category postCategory;

    @JsonBackReference(value = "posterAccount")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId", referencedColumnName = "accountId")
    @NonNull
    private Account posterAccount;

    private LocalDateTime updatedAt;
}
