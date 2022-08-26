package com.example.sellthatthing.repositories;

import com.example.sellthatthing.models.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByCode(String token);

    @Transactional
    @Modifying
    @Query("UPDATE VerificationCode c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.code = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
