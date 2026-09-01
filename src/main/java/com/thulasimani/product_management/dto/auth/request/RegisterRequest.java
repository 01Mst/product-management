package com.thulasimani.product_management.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Setter;

public record RegisterRequest(

        @NotBlank(message = "User is required")
        @Size(min=3, max=50)
        String username,

        @NotBlank(message = "Password is required")
        @Size(min=8, max = 50)
        String password

) {
}
