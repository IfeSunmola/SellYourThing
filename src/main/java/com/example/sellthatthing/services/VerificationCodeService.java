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

    public void updateConfirmedAtById(Long codeId) {
        verificationCodeRepository.updateConfirmedAtById(codeId, LocalDateTime.now());
    }

    public VerificationCode findByCodeId(Long codeId) {
        return verificationCodeRepository.findById(codeId).orElseThrow(()
                -> new ResourceNotFoundException("Code id '" + codeId + "' was not found"));
    }

    public VerificationCode save(VerificationCode code) {
        return verificationCodeRepository.save(code);
    }

    public void deleteById(Long codeId) {
        verificationCodeRepository.deleteById(codeId);
    }
}
