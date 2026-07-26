package com.tekhoufeha.auth.controller;

import com.tekhoufeha.auth.dto.request.LoginRequest;
import com.tekhoufeha.auth.dto.request.RefreshTokenRequest;
import com.tekhoufeha.auth.dto.request.RegisterRequest;
import com.tekhoufeha.auth.dto.response.LoginResponse;
import com.tekhoufeha.auth.dto.response.RefreshTokenResponse;
import com.tekhoufeha.auth.dto.response.RegisterResponse;
import com.tekhoufeha.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(request));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        return ResponseEntity.ok(
                authService.refreshToken(request)
        );
    }
}