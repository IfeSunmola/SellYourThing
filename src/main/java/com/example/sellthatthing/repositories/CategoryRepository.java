package com.example.sellthatthing.repositories;

import com.example.sellthatthing.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CategoryRepository extends JpaRepository<Category, Long> {
    void deleteByNameIgnoreCase(String categoryName);

    boolean existsByNameIgnoreCase(String categoryName);

    Optional<Category> findByNameIgnoreCase(String categoryName);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.name = ?2 WHERE c.name = ?1")
    void editName(String oldName, String newName);
}
