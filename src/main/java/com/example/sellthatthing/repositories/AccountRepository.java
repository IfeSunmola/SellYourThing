package com.example.sellthatthing.repositories;

import com.example.sellthatthing.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findPostsByAccountId(Long accountId);
    boolean existsByEmail(String email);
}
