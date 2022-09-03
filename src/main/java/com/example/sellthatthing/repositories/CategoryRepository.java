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
    boolean existsByCategoryName(String categoryName);

    void deleteByCategoryName(String categoryName);

    Optional<Category> findByCategoryName(String categoryName);

    @Transactional
    @Modifying
    @Query("UPDATE Category c SET c.categoryName = ?2 WHERE c.categoryName = ?1")
    void updateName(String oldName, String newName);
}
