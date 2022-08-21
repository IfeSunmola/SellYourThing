package com.example.sellthatthing.ConfirmationToken;


import com.example.sellthatthing.Account.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class ConfirmationToken {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long tokenId;
    @NonNull private String token;
    @NonNull private LocalDateTime createdAt;
    @NonNull private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "accountId")
    @NonNull private Account account;
}