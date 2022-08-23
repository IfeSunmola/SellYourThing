package com.example.sellthatthing.services;

import com.example.sellthatthing.exceptions.EmptyResourceException;
import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.models.Location;
import com.example.sellthatthing.repositories.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> findAll() {
        List<Location> listOfLocations = locationRepository.findAll();
        if (listOfLocations.isEmpty()) {
            throw new EmptyResourceException("No posts found");
        }
        return listOfLocations;
    }

    public Location findByLocationId(Long locationId) {
        return locationRepository.findById(locationId).orElseThrow(()
                -> new ResourceNotFoundException("Location id: '" + locationId + "' was not found"));
    }
}
