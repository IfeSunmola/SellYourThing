package com.example.sellthatthing.repositories;

import com.example.sellthatthing.models.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
@Transactional(readOnly = true)
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE VerificationCode c SET c.confirmedAt = ?2 WHERE c.codeId = ?1")
    int updateConfirmedAtById(Long codeId, LocalDateTime now);
}
