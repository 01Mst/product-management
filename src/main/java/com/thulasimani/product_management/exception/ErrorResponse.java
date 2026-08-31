package com.thulasimani.product_management.exception;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
