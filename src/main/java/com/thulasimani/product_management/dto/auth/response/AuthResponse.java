package com.thulasimani.product_management.dto.auth.response;

public record AuthResponse(

        String accessToken,
        String refreshToken,
        String tokenType

) {
}
