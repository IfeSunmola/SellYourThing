package com.example.sellthatthing.services;

import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.models.AccountDetails;
import com.example.sellthatthing.models.City;
import com.example.sellthatthing.repositories.CityRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final Logger logger = LoggerFactory.getLogger(CityService.class);

    public List<City> findAll() {
        return cityRepository.findAll();
    }

    public City findByCityName(String cityName) {
        return cityRepository.findByName(cityName).orElseThrow(()
                -> new ResourceNotFoundException("City: '" + cityName + "' was not found"));
    }

    public void edit(String oldName, String newName, HashMap<String, String> message, HttpServletRequest request) {
        newName = StringUtils.capitalize(newName.toLowerCase(Locale.ROOT));
        message.clear();
        if (cityRepository.existsByNameIgnoreCase(newName)) {
            message.put("editCityStatus", "false");
            message.put("editCityMessage", "City <strong>'" + newName + "'</strong> already exists. Edit failed");
            return;
        }
        if (oldName.equalsIgnoreCase(newName)) {
            message.put("editCityStatus", "false");
            message.put("editCityMessage", "New name must be different from old name. Edit failed");
            return;
        }
        cityRepository.editName(oldName, newName);
        logger.info("City: " + oldName + " was edited to " + newName + " by " + getAccountInfo(request));
        message.put("editCityStatus", "true");
        message.put("editCityMessage", "<strong>" + oldName + "</strong> has been updated to <strong>" + newName + "</strong>");
    }

    @Transactional
    public void delete(String cityName, HashMap<String, String> message, HttpServletRequest request) {
        message.clear();
        if (cityRepository.existsByNameIgnoreCase(cityName)) {
            message.put("deleteCityStatus", "true");
            message.put("deleteCityMessage", "City <strong>" + cityName + "</strong> has been deleted");
            logger.warn("City: " + cityName + " has been deleted by " + getAccountInfo(request));
            cityRepository.deleteByNameIgnoreCase(cityName);
        }
        else {
            message.put("deleteCityStatus", "false");
            message.put("deleteCityMessage", "City <strong>" + cityName + "</strong> was not found");
        }
    }

    private String getAccountInfo(HttpServletRequest request) {
        Authentication auth = (Authentication) request.getUserPrincipal();
        AccountDetails authAccount = (AccountDetails) auth.getPrincipal();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh:mm:ss a"));
        return authAccount.firstName() + " " + authAccount.lastName() + " (" + authAccount.accountId() + ") at " + time;
    }

    @Transactional
    public void save(String newCityName, HashMap<String, String> message, HttpServletRequest request) {
        newCityName = StringUtils.capitalize(newCityName.toLowerCase(Locale.ROOT));
        message.clear();
        if (cityRepository.existsByNameIgnoreCase(newCityName)) {
            message.put("addCityStatus", "false");
            message.put("addCityMessage", "City <strong>" + newCityName + "</strong> already exists");
            return;
        }
        cityRepository.save(new City(newCityName, LocalDateTime.now()));
        logger.info("A new city was added by: " + getAccountInfo(request));
        message.put("addCityStatus", "true");
        message.put("addCityMessage", "<strong>" + newCityName + "</strong> has been added");
    }
}
