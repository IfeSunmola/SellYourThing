package com.example.sellthatthing.models;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long postId;
    @NonNull private String title;
    @NonNull @Column(length = 3000) private String body;
    @NonNull private LocalDateTime createdAt;
    @NonNull private BigDecimal price;
    @NonNull private String imageUrl;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cityName", referencedColumnName = "id")
    @NonNull
    private City postCity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    @NonNull
    private Category postCategory;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "accountId", referencedColumnName = "accountId")
    @NonNull
    private Account posterAccount;

    private LocalDateTime updatedAt;


    // delete later, only used for test data
    public Post(@NonNull String title, @NonNull String body, @NonNull LocalDateTime createdAt, @NonNull BigDecimal price, @NonNull City postCity, @NonNull Category postCategory, @NonNull Account posterAccount) {
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.price = price;
        this.postCity = postCity;
        this.postCategory = postCategory;
        this.posterAccount = posterAccount;
    }
}
