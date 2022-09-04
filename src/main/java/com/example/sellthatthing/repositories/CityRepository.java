package com.example.sellthatthing.repositories;

import com.example.sellthatthing.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByCityName(String cityName);
}
