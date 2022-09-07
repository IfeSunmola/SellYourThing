package com.example.sellyourthing.repositories;

import com.example.sellyourthing.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE City c SET c.name = ?2 WHERE c.name = ?1")
    void editName(String oldName, String newName);

    boolean existsByNameIgnoreCase(String name);

    void deleteByNameIgnoreCase(String name);
}
