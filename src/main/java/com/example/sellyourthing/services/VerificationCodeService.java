package com.example.sellyourthing.services;

import com.example.sellyourthing.exceptions.ResourceNotFoundException;
import com.example.sellyourthing.models.VerificationCode;
import com.example.sellyourthing.repositories.VerificationCodeRepository;
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
