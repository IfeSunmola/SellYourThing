package com.example.sellthatthing.services;

import com.example.sellthatthing.models.ConfirmationToken;
import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.repositories.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken findByToken(String token) {
        return confirmationTokenRepository.findByToken(token).orElseThrow(()
                -> new ResourceNotFoundException("Invalid Token"));
    }

    public int updateConfirmedAt(String token){
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
