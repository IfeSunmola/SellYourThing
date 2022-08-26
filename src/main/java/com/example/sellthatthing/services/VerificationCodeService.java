package com.example.sellthatthing.services;

import com.example.sellthatthing.exceptions.ResourceNotFoundException;
import com.example.sellthatthing.models.VerificationCode;
import com.example.sellthatthing.repositories.VerificationCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VerificationCodeService {
    private final VerificationCodeRepository verificationCodeRepository;

    public void saveCode(VerificationCode code) {
        verificationCodeRepository.save(code);
    }

    public VerificationCode findByCode(String token) {
        return verificationCodeRepository.findByCode(token).orElseThrow(()
                -> new ResourceNotFoundException("Invalid Code"));
    }

    public int updateConfirmedAt(String code){
        return verificationCodeRepository.updateConfirmedAt(code, LocalDateTime.now());
    }
}
