package com.example.sellthatthing.repositories;

import com.example.sellthatthing.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findPostsByAccountId(Long accountId);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE Account a " + "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);

    Optional<Account> findByEmail(String email);
}
