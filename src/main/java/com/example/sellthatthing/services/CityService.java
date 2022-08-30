package com.example.sellthatthing.services;

import com.example.sellthatthing.exceptions.EmptyResourceException;
import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.models.City;
import com.example.sellthatthing.repositories.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<City> findAll() {
        List<City> listOfCities = cityRepository.findAll();
        if (listOfCities.isEmpty()) {
            throw new EmptyResourceException("No posts found");
        }
        return listOfCities;
    }

    public City findByCityId(Long cityId) {
        return cityRepository.findById(cityId).orElseThrow(()
                -> new ResourceNotFoundException("City id: '" + cityId + "' was not found"));
    }
}