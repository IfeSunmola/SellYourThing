package com.example.sellthatthing.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmptyResourceException extends RuntimeException {
    private final String message;
}
