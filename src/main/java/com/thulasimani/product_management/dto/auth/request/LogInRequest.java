package com.thulasimani.product_management.dto.auth.request;

import jakarta.validation.constraints.NotBlank;

public record LogInRequest(

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password

) {
}
