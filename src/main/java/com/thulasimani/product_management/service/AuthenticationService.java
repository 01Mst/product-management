package com.thulasimani.product_management.service;

import com.thulasimani.product_management.dto.auth.request.LogInRequest;
import com.thulasimani.product_management.dto.auth.request.RefreshTokenRequest;
import com.thulasimani.product_management.dto.auth.request.RegisterRequest;
import com.thulasimani.product_management.dto.auth.response.AuthResponse;

public interface AuthenticationService {

    void register(RegisterRequest request);
    AuthResponse login(LogInRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    void logout(RefreshTokenRequest request);

}
