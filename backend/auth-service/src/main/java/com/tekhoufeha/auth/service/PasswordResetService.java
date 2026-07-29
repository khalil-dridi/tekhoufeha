package com.tekhoufeha.auth.service;

import com.tekhoufeha.auth.entity.AuthUser;
import com.tekhoufeha.auth.entity.PasswordResetToken;
import com.tekhoufeha.auth.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PasswordResetService {


    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;


    @Value("${password-reset.expiration}")
    private long tokenExpiration;



    public PasswordResetToken createToken(AuthUser authUser) {


        PasswordResetToken token =
                PasswordResetToken.builder()
                        .token(UUID.randomUUID().toString())
                        .expiryDate(
                                Instant.now()
                                        .plusMillis(tokenExpiration)
                        )
                        .authUser(authUser)
                        .build();


        PasswordResetToken savedToken =
                passwordResetTokenRepository.save(token);


        emailService.sendPasswordResetEmail(
                authUser.getEmail(),
                savedToken.getToken()
        );


        return savedToken;
    }



    public Optional<PasswordResetToken> findByToken(String token) {

        return passwordResetTokenRepository.findByToken(token);
    }



    public PasswordResetToken verifyExpiration(
            PasswordResetToken token) {


        if (token.getExpiryDate()
                .isBefore(Instant.now())) {


            passwordResetTokenRepository.delete(token);


            throw new RuntimeException(
                    "Password reset token expired."
            );
        }


        return token;
    }



    public void delete(PasswordResetToken token) {

        passwordResetTokenRepository.delete(token);
    }
}