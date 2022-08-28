package com.example.sellthatthing.datatransferobjects;

public record VerificationDto(Long codeId, Long accountId, String rawPassword) {
}
