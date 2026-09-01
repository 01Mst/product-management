package com.thulasimani.product_management.controller;

import com.thulasimani.product_management.dto.auth.request.LogInRequest;
import com.thulasimani.product_management.dto.auth.request.RefreshTokenRequest;
import com.thulasimani.product_management.dto.auth.request.RegisterRequest;
import com.thulasimani.product_management.dto.auth.response.AuthResponse;
import com.thulasimani.product_management.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication and token management APIs")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(
            summary = "Register user",
            description = "Registers a new USER account"
    )
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request){
        authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "Authenticates a user and returns access and refresh tokens"
    )
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LogInRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Refresh access token",
            description = "Rotates the refresh token and generates a new access token"
    )
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request){
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Logout",
            description = "Revokes the refresh token"
    )
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequest request){
        authenticationService.logout(request);
        return ResponseEntity.noContent().build();
    }


}
