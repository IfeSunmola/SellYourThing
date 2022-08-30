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
    private String imageUrl;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cityId", referencedColumnName = "cityId")
    @NonNull
    private City postCity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    @NonNull
    private Category postCategory;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "accountId", referencedColumnName = "accountId")
    @NonNull
    private Account posterAccount;

    private LocalDateTime updatedAt;

    public String getFormattedCreateAtDate() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(createdAt);
    }
}
