package com.example.sellthatthing.repositories;

import com.example.sellthatthing.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Account a  SET a.enabled = TRUE WHERE a.accountId = ?1")
    int enableAppUserById(Long accountId);

    Optional<Account> findByEmail(String email);
}
