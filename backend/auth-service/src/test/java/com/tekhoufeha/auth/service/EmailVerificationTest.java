package com.tekhoufeha.auth.service;

import com.tekhoufeha.auth.entity.AuthUser;
import com.tekhoufeha.auth.entity.EmailVerificationToken;
import com.tekhoufeha.auth.entity.UserStatus;
import com.tekhoufeha.auth.repository.EmailVerificationTokenRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmailVerificationTest {


    @Mock
    private EmailVerificationTokenRepository emailVerificationTokenRepository;


    @Mock
    private EmailService emailService;


    @InjectMocks
    private EmailVerificationService emailVerificationService;



    @Test
    void createToken_should_save_and_send_email() {

        AuthUser user = AuthUser.builder()
                .email("test@test.com")
                .status(UserStatus.PENDING)
                .build();


        EmailVerificationToken token =
                EmailVerificationToken.builder()
                        .token("test-token")
                        .authUser(user)
                        .expiryDate(
                                Instant.now().plusSeconds(3600)
                        )
                        .build();


        when(emailVerificationTokenRepository.save(any()))
                .thenReturn(token);



        EmailVerificationToken result =
                emailVerificationService.createToken(user);



        assertThat(result.getToken())
                .isEqualTo("test-token");


        verify(emailVerificationTokenRepository)
                .save(any());


        verify(emailService)
                .sendVerificationEmail(
                        user.getEmail(),
                        token.getToken()
                );
    }





    @Test
    void verifyEmail_should_activate_user_when_token_is_valid() {


        AuthUser user = AuthUser.builder()
                .email("test@test.com")
                .status(UserStatus.PENDING)
                .build();


        EmailVerificationToken token =
                EmailVerificationToken.builder()
                        .token("valid-token")
                        .authUser(user)
                        .expiryDate(
                                Instant.now().plusSeconds(3600)
                        )
                        .build();



        when(emailVerificationTokenRepository.findByToken("valid-token"))
                .thenReturn(Optional.of(token));



        AuthUser result =
                emailVerificationService.verifyEmail(
                        "valid-token"
                );



        assertThat(result.getStatus())
                .isEqualTo(UserStatus.ACTIVE);



        verify(emailVerificationTokenRepository)
                .delete(token);
    }





    @Test
    void verifyEmail_should_fail_when_token_not_found() {


        when(emailVerificationTokenRepository.findByToken("invalid"))
                .thenReturn(Optional.empty());



        org.assertj.core.api.Assertions.assertThatThrownBy(() ->
                        emailVerificationService.verifyEmail("invalid")
                )
                .isInstanceOf(RuntimeException.class);
    }
}