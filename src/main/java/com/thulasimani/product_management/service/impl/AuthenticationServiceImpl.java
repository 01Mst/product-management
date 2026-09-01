package com.thulasimani.product_management.service.impl;

import com.thulasimani.product_management.dto.auth.request.LogInRequest;
import com.thulasimani.product_management.dto.auth.request.RefreshTokenRequest;
import com.thulasimani.product_management.dto.auth.request.RegisterRequest;
import com.thulasimani.product_management.dto.auth.response.AuthResponse;
import com.thulasimani.product_management.entity.RefreshToken;
import com.thulasimani.product_management.entity.User;
import com.thulasimani.product_management.enums.Role;
import com.thulasimani.product_management.repository.RefreshTokenRepository;
import com.thulasimani.product_management.repository.UserRepository;
import com.thulasimani.product_management.security.JwtService;
import com.thulasimani.product_management.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public void register(RegisterRequest request) {

        if(userRepository.existsByUsername(request.username())){
            throw new IllegalArgumentException("Username already esists");
        }

        User user=User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

    }

    @Override
    @Transactional
    public AuthResponse login(LogInRequest request) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        String accessToken= jwtService.generateToken(userDetails);
        return null;
    }


    private String createRefreshToken(String username){
        User user=userRepository.findByUsername(username)
                .orElseThrow(()->new IllegalArgumentException("User not found"));
        String token= UUID.randomUUID().toString();
        RefreshToken refreshToken=RefreshToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);
        return token;
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken existingToken=refreshTokenRepository.findByToken(request.refreshToken())
                .orElseThrow(()->new IllegalArgumentException("Invalid refresh token"));
        if(existingToken.isRevoked()){
            throw new IllegalArgumentException("Refresh token has been revoked");
        }
        if(existingToken.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Refresh token has expired");
        }
        User user=existingToken.getUser();
        existingToken.setRevoked(true);
        refreshTokenRepository.save(existingToken);
        UserDetails userDetails=new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                java.util.List.of(
                        new SimpleGrantedAuthority("Role_"+user.getRole().name())
                )
        );
        String newAccessToken=jwtService.generateToken(userDetails);
        String newRefreshToken=createRefreshToken(user.getUsername());

        return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                "Bearer"
        );
    }

    @Override
    @Transactional
    public void logout(RefreshTokenRequest request) {
        RefreshToken refreshToken=refreshTokenRepository.findByToken(
                request.refreshToken()
        ).orElseThrow(()->new IllegalArgumentException("Invalid refresh token"));
    refreshToken.setRevoked(true);
    refreshTokenRepository.save(refreshToken);
    }
}
