package com.tekhoufeha.auth.service;

import com.tekhoufeha.auth.entity.AuthUser;
import com.tekhoufeha.auth.entity.RefreshToken;
import com.tekhoufeha.auth.exception.RefreshTokenException;
import com.tekhoufeha.auth.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${refresh-token.expiration}")
    private long refreshTokenExpiration;


    public RefreshToken createRefreshToken(AuthUser authUser) {

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenExpiration))
                .authUser(authUser)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }


    public Optional<RefreshToken> findByToken(String token) {

        return refreshTokenRepository.findByToken(token);
    }


    public RefreshToken verifyExpiration(RefreshToken refreshToken) {

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {

            refreshTokenRepository.delete(refreshToken);

            throw new RefreshTokenException(
                    "Refresh token has expired."
            );
        }

        return refreshToken;
    }


    public void delete(RefreshToken refreshToken) {

        refreshTokenRepository.delete(refreshToken);
    }


    public void deleteByUserId(UUID userId) {

        refreshTokenRepository.deleteAllByAuthUser_Id(userId);
    }
}