package com.example.sellthatthing.models;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class VerificationCode {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long codeId;
    @NonNull private final String code = UUID.randomUUID().toString();
    @NonNull private final LocalDateTime createdAt = LocalDateTime.now();
    @NonNull private final LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "accountId")
    @NonNull private Account account;
}
