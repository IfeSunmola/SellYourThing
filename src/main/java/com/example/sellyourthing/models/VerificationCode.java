package com.example.sellyourthing.models;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class VerificationCode {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long codeId;
    @NonNull private final String code = UUID.randomUUID().toString().toUpperCase(Locale.ROOT).substring(0, 6);
    @NonNull private final LocalDateTime createdAt = LocalDateTime.now();
    @NonNull private final LocalDateTime expiresAt = LocalDateTime.now().plusHours(24);
    private LocalDateTime confirmedAt;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false, name = "accountId")
    @NonNull private Account account;
}
