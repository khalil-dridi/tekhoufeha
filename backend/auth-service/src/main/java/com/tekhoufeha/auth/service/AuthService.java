package com.tekhoufeha.auth.service;

import com.tekhoufeha.auth.dto.request.ForgotPasswordRequest;
import com.tekhoufeha.auth.dto.request.LoginRequest;
import com.tekhoufeha.auth.dto.request.RefreshTokenRequest;
import com.tekhoufeha.auth.dto.request.RegisterRequest;
import com.tekhoufeha.auth.dto.request.ResetPasswordRequest;

import com.tekhoufeha.auth.dto.response.LoginResponse;
import com.tekhoufeha.auth.dto.response.RefreshTokenResponse;
import com.tekhoufeha.auth.dto.response.RegisterResponse;

import com.tekhoufeha.auth.dto.response.UserProfileResponse;
import com.tekhoufeha.auth.entity.AuthUser;
import com.tekhoufeha.auth.entity.PasswordResetToken;
import com.tekhoufeha.auth.entity.RefreshToken;
import com.tekhoufeha.auth.entity.UserStatus;

import com.tekhoufeha.auth.exception.EmailAlreadyExistsException;
import com.tekhoufeha.auth.exception.InvalidCredentialsException;
import com.tekhoufeha.auth.exception.PasswordMismatchException;
import com.tekhoufeha.auth.exception.RefreshTokenException;

import com.tekhoufeha.auth.mapper.AuthMapper;
import com.tekhoufeha.auth.repository.AuthUserRepository;
import com.tekhoufeha.auth.security.JwtService;

import com.tekhoufeha.auth.dto.request.ChangePasswordRequest;
import com.tekhoufeha.auth.entity.AuthProvider;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {


    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final EmailVerificationService emailVerificationService;

    private final PasswordResetService passwordResetService;



    public RegisterResponse register(RegisterRequest request) {


        if (authUserRepository.existsByEmail(request.email())) {

            throw new EmailAlreadyExistsException(
                    "Email already exists."
            );
        }


        if (!request.password()
                .equals(request.confirmPassword())) {

            throw new PasswordMismatchException(
                    "Passwords do not match."
            );
        }


        String encodedPassword =
                passwordEncoder.encode(request.password());


        AuthUser authUser =
                authMapper.toEntity(
                        request,
                        encodedPassword
                );


        authUserRepository.save(authUser);


        emailVerificationService.createToken(authUser);


        return new RegisterResponse(
                "Registration successful. Please verify your email."
        );
    }




    public LoginResponse login(LoginRequest request) {


        AuthUser authUser =
                authUserRepository.findByEmail(request.email())
                        .orElseThrow(() ->
                                new InvalidCredentialsException(
                                        "Invalid email or password."
                                ));


        if (authUser.getStatus() != UserStatus.ACTIVE) {

            throw new InvalidCredentialsException(
                    "Please verify your email before login."
            );
        }


        if (!passwordEncoder.matches(
                request.password(),
                authUser.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid email or password."
            );
        }


        String accessToken =
                jwtService.generateToken(authUser);


        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(authUser);



        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }




    public RefreshTokenResponse refreshToken(
            RefreshTokenRequest request) {


        RefreshToken oldRefreshToken =
                refreshTokenService
                        .findByToken(request.refreshToken())
                        .orElseThrow(() ->
                                new RefreshTokenException(
                                        "Refresh token not found."
                                ));


        refreshTokenService.verifyExpiration(
                oldRefreshToken
        );


        AuthUser authUser =
                oldRefreshToken.getAuthUser();



        String newAccessToken =
                jwtService.generateToken(authUser);



        RefreshToken newRefreshToken =
                refreshTokenService
                        .rotateRefreshToken(oldRefreshToken);



        return RefreshTokenResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }




    public void verifyEmail(String token) {


        AuthUser authUser =
                emailVerificationService.verifyEmail(token);


        authUserRepository.save(authUser);
    }





    public void forgotPassword(
            ForgotPasswordRequest request) {


        authUserRepository.findByEmail(request.email())
                .ifPresent(passwordResetService::createToken);

    }





    public void resetPassword(
            ResetPasswordRequest request) {


        PasswordResetToken resetToken =
                passwordResetService
                        .findByToken(request.token())
                        .orElseThrow(() ->
                                new InvalidCredentialsException(
                                        "Invalid reset token."
                                ));


        passwordResetService.verifyExpiration(
                resetToken
        );


        AuthUser authUser =
                resetToken.getAuthUser();



        authUser.setPassword(
                passwordEncoder.encode(
                        request.newPassword()
                )
        );


        authUserRepository.save(authUser);



        // Déconnexion de toutes les sessions actives
        refreshTokenService.deleteByUserId(
                authUser.getId()
        );



        passwordResetService.delete(
                resetToken
        );
    }



    public UserProfileResponse getCurrentUser(Authentication authentication) {


        String email = authentication.getName();


        AuthUser authUser =
                authUserRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new InvalidCredentialsException(
                                        "User not found."
                                ));


        return new UserProfileResponse(
                authUser.getId(),
                authUser.getEmail(),
                authUser.getRole(),
                authUser.getStatus(),
                authUser.getProvider()
        );
    }


    public void changePassword(
            ChangePasswordRequest request,
            Authentication authentication) {


        String email = authentication.getName();


        AuthUser authUser =
                authUserRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new InvalidCredentialsException(
                                        "User not found."
                                ));




        // Les comptes Google n'ont pas de password
        if (authUser.getProvider() == AuthProvider.GOOGLE) {

            throw new InvalidCredentialsException(
                    "Google users cannot change password."
            );
        }




        // Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(
                request.currentPassword(),
                authUser.getPassword())) {


            throw new InvalidCredentialsException(
                    "Current password is incorrect."
            );
        }




        // Vérifier confirmation
        if (!request.newPassword()
                .equals(request.confirmPassword())) {


            throw new PasswordMismatchException(
                    "Passwords do not match."
            );
        }




        // Encoder nouveau password
        authUser.setPassword(
                passwordEncoder.encode(
                        request.newPassword()
                )
        );


        authUserRepository.save(authUser);



        // Sécurité :
        // invalider toutes les sessions existantes
        refreshTokenService.deleteByUserId(
                authUser.getId()
        );
    }





    public void logout(
            RefreshTokenRequest request) {


        RefreshToken refreshToken =
                refreshTokenService
                        .findByToken(request.refreshToken())
                        .orElseThrow(() ->
                                new RefreshTokenException(
                                        "Refresh token not found."
                                ));


        refreshTokenService.delete(refreshToken);
    }

}