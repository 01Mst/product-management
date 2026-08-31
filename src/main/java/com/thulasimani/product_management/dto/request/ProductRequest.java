package com.thulasimani.product_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductRequest(
        @NotBlank(message = "Product name is required")
        @Size(max=255, message= "Product name must not exceed 255 characters")
        String productName
) {
}
