package com.tekhoufeha.auth.service;

import com.tekhoufeha.auth.dto.request.LoginRequest;
import com.tekhoufeha.auth.dto.request.RegisterRequest;
import com.tekhoufeha.auth.dto.response.LoginResponse;
import com.tekhoufeha.auth.dto.response.RegisterResponse;
import com.tekhoufeha.auth.entity.AuthUser;
import com.tekhoufeha.auth.exception.EmailAlreadyExistsException;
import com.tekhoufeha.auth.exception.InvalidCredentialsException;
import com.tekhoufeha.auth.exception.PasswordMismatchException;
import com.tekhoufeha.auth.mapper.AuthMapper;
import com.tekhoufeha.auth.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        if (authUserRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        if (!request.password().equals(request.confirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match.");
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        AuthUser authUser = authMapper.toEntity(request, encodedPassword);

        authUserRepository.save(authUser);

        return new RegisterResponse("Registration successful.");
    }


    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        AuthUser authUser = authUserRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password."));

        if (!passwordEncoder.matches(request.password(), authUser.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }

        return new LoginResponse("Login successful.");
    }
}