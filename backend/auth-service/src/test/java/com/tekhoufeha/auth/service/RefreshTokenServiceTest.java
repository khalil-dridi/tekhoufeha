package com.tekhoufeha.auth.service;

import com.tekhoufeha.auth.entity.AuthUser;
import com.tekhoufeha.auth.entity.RefreshToken;
import com.tekhoufeha.auth.entity.Role;
import com.tekhoufeha.auth.entity.UserStatus;
import com.tekhoufeha.auth.exception.RefreshTokenException;
import com.tekhoufeha.auth.repository.RefreshTokenRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {


    @Mock
    private RefreshTokenRepository refreshTokenRepository;


    @InjectMocks
    private RefreshTokenService refreshTokenService;



    @Test
    void createRefreshToken_should_create_and_save_token() {

        AuthUser user = createUser();


        RefreshToken refreshToken =
                RefreshToken.builder()
                        .id(1L)
                        .token(UUID.randomUUID().toString())
                        .expiryDate(Instant.now().plusSeconds(3600))
                        .authUser(user)
                        .build();


        when(refreshTokenRepository.save(any(RefreshToken.class)))
                .thenReturn(refreshToken);



        RefreshToken result =
                refreshTokenService.createRefreshToken(user);



        assertThat(result)
                .isNotNull();

        assertThat(result.getAuthUser())
                .isEqualTo(user);


        verify(refreshTokenRepository)
                .save(any(RefreshToken.class));
    }





    @Test
    void verifyExpiration_should_return_token_when_not_expired() {


        RefreshToken refreshToken =
                RefreshToken.builder()
                        .expiryDate(Instant.now().plusSeconds(3600))
                        .build();



        RefreshToken result =
                refreshTokenService.verifyExpiration(refreshToken);



        assertThat(result)
                .isEqualTo(refreshToken);


        verify(refreshTokenRepository, never())
                .delete(any());
    }





    @Test
    void verifyExpiration_should_throw_exception_when_expired() {


        RefreshToken refreshToken =
                RefreshToken.builder()
                        .expiryDate(Instant.now().minusSeconds(3600))
                        .build();



        assertThatThrownBy(() ->
                refreshTokenService.verifyExpiration(refreshToken)
        )
                .isInstanceOf(RefreshTokenException.class)
                .hasMessage("Refresh token has expired.");



        verify(refreshTokenRepository)
                .delete(refreshToken);
    }





    @Test
    void rotateRefreshToken_should_delete_old_and_create_new_token() {


        AuthUser user = createUser();


        RefreshToken oldToken =
                RefreshToken.builder()
                        .token("old-token")
                        .authUser(user)
                        .build();


        RefreshToken newToken =
                RefreshToken.builder()
                        .token("new-token")
                        .authUser(user)
                        .build();



        when(refreshTokenRepository.save(any(RefreshToken.class)))
                .thenReturn(newToken);



        RefreshToken result =
                refreshTokenService.rotateRefreshToken(oldToken);



        assertThat(result.getToken())
                .isEqualTo("new-token");


        verify(refreshTokenRepository)
                .delete(oldToken);


        verify(refreshTokenRepository)
                .save(any(RefreshToken.class));
    }





    private AuthUser createUser() {

        return AuthUser.builder()
                .email("test@test.com")
                .password("encodedPassword")
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
    }
}