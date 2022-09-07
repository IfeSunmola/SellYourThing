package com.example.sellyourthing.datatransferobjects;

public record VerificationDto(Long codeId, Long accountId, String rawPassword) {
}
