package com.tekhoufeha.auth.service;

import com.tekhoufeha.auth.entity.AuthUser;
import com.tekhoufeha.auth.entity.EmailVerificationToken;
import com.tekhoufeha.auth.exception.RefreshTokenException;
import com.tekhoufeha.auth.repository.EmailVerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.tekhoufeha.auth.entity.UserStatus;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {


    private final EmailVerificationTokenRepository emailVerificationTokenRepository;


    @Value("${email-verification.expiration}")
    private long tokenExpiration;



    public EmailVerificationToken createToken(AuthUser authUser) {

        EmailVerificationToken token =
                EmailVerificationToken.builder()
                        .token(UUID.randomUUID().toString())
                        .expiryDate(
                                Instant.now()
                                        .plusMillis(tokenExpiration)
                        )
                        .authUser(authUser)
                        .build();


        return emailVerificationTokenRepository.save(token);
    }



    public Optional<EmailVerificationToken> findByToken(String token) {

        return emailVerificationTokenRepository.findByToken(token);
    }



    public EmailVerificationToken verifyExpiration(
            EmailVerificationToken token) {


        if (token.getExpiryDate()
                .isBefore(Instant.now())) {


            emailVerificationTokenRepository.delete(token);


            throw new RuntimeException(
                    "Email verification token expired."
            );
        }


        return token;
    }



    public void delete(EmailVerificationToken token) {

        emailVerificationTokenRepository.delete(token);
    }

    public AuthUser verifyEmail(String token) {

        EmailVerificationToken verificationToken =
                emailVerificationTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new  RefreshTokenException("Invalid verification token.")
                        );


        verifyExpiration(verificationToken);


        AuthUser authUser = verificationToken.getAuthUser();


        authUser.setStatus(UserStatus.ACTIVE);


        emailVerificationTokenRepository.delete(verificationToken);


        return authUser;
    }
}