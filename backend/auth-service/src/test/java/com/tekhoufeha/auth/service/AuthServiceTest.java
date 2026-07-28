package com.tekhoufeha.auth.service;

import com.tekhoufeha.auth.dto.request.LoginRequest;
import com.tekhoufeha.auth.dto.request.RefreshTokenRequest;
import com.tekhoufeha.auth.dto.response.LoginResponse;
import com.tekhoufeha.auth.dto.response.RefreshTokenResponse;
import com.tekhoufeha.auth.entity.AuthUser;
import com.tekhoufeha.auth.entity.RefreshToken;
import com.tekhoufeha.auth.entity.Role;
import com.tekhoufeha.auth.entity.UserStatus;
import com.tekhoufeha.auth.exception.EmailAlreadyExistsException;
import com.tekhoufeha.auth.exception.InvalidCredentialsException;
import com.tekhoufeha.auth.exception.PasswordMismatchException;
import com.tekhoufeha.auth.exception.RefreshTokenException;
import com.tekhoufeha.auth.mapper.AuthMapper;
import com.tekhoufeha.auth.repository.AuthUserRepository;
import com.tekhoufeha.auth.security.JwtService;
import com.tekhoufeha.auth.dto.request.RegisterRequest;
import com.tekhoufeha.auth.dto.response.RegisterResponse;
import com.tekhoufeha.auth.service.EmailVerificationService;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {


    @Mock
    private AuthUserRepository authUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthMapper authMapper;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private EmailVerificationService emailVerificationService;

    @InjectMocks
    private AuthService authService;



    @Test
    void login_should_return_tokens_when_credentials_are_valid() {

        LoginRequest request =
                new LoginRequest(
                        "test@test.com",
                        "password123"
                );


        AuthUser user = createUser();


        RefreshToken refreshToken =
                RefreshToken.builder()
                        .token("refresh-token-value")
                        .authUser(user)
                        .build();


        when(authUserRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(user));


        when(passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )).thenReturn(true);


        when(jwtService.generateToken(user))
                .thenReturn("access-token-value");


        when(refreshTokenService.createRefreshToken(user))
                .thenReturn(refreshToken);



        LoginResponse response =
                authService.login(request);



        assertThat(response.accessToken())
                .isEqualTo("access-token-value");

        assertThat(response.refreshToken())
                .isEqualTo("refresh-token-value");

        assertThat(response.tokenType())
                .isEqualTo("Bearer");


        verify(authUserRepository)
                .findByEmail(request.email());

        verify(refreshTokenService)
                .createRefreshToken(user);
    }





    @Test
    void login_should_throw_exception_when_password_is_invalid() {


        LoginRequest request =
                new LoginRequest(
                        "test@test.com",
                        "wrongPassword"
                );


        AuthUser user = createUser();


        when(authUserRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(user));


        when(passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )).thenReturn(false);



        assertThatThrownBy(() ->
                authService.login(request)
        )
                .isInstanceOf(InvalidCredentialsException.class);



        verify(jwtService, never())
                .generateToken(any());


        verify(refreshTokenService, never())
                .createRefreshToken(any());
    }





    @Test
    void login_should_throw_exception_when_user_not_found() {


        LoginRequest request =
                new LoginRequest(
                        "unknown@test.com",
                        "password123"
                );


        when(authUserRepository.findByEmail(request.email()))
                .thenReturn(Optional.empty());



        assertThatThrownBy(() ->
                authService.login(request)
        )
                .isInstanceOf(InvalidCredentialsException.class);



        verify(passwordEncoder, never())
                .matches(any(), any());
    }





    @Test
    void refreshToken_should_return_new_tokens_when_refresh_token_is_valid() {


        RefreshTokenRequest request =
                new RefreshTokenRequest(
                        "old-refresh-token"
                );


        AuthUser user = createUser();


        RefreshToken oldToken =
                RefreshToken.builder()
                        .token("old-refresh-token")
                        .authUser(user)
                        .build();


        RefreshToken newToken =
                RefreshToken.builder()
                        .token("new-refresh-token")
                        .authUser(user)
                        .build();



        when(refreshTokenService.findByToken(
                request.refreshToken()
        ))
                .thenReturn(Optional.of(oldToken));


        when(jwtService.generateToken(user))
                .thenReturn("new-access-token");


        when(refreshTokenService.rotateRefreshToken(oldToken))
                .thenReturn(newToken);



        RefreshTokenResponse response =
                authService.refreshToken(request);



        assertThat(response.accessToken())
                .isEqualTo("new-access-token");


        assertThat(response.refreshToken())
                .isEqualTo("new-refresh-token");


        verify(refreshTokenService)
                .rotateRefreshToken(oldToken);
    }





    @Test
    void refreshToken_should_throw_exception_when_token_not_found() {


        RefreshTokenRequest request =
                new RefreshTokenRequest(
                        "invalid-token"
                );


        when(refreshTokenService.findByToken(
                request.refreshToken()
        ))
                .thenReturn(Optional.empty());



        assertThatThrownBy(() ->
                authService.refreshToken(request)
        )
                .isInstanceOf(RefreshTokenException.class);
    }





    @Test
    void logout_should_delete_refresh_token() {


        RefreshTokenRequest request =
                new RefreshTokenRequest(
                        "refresh-token"
                );


        RefreshToken refreshToken =
                RefreshToken.builder()
                        .token("refresh-token")
                        .build();



        when(refreshTokenService.findByToken(
                request.refreshToken()
        ))
                .thenReturn(Optional.of(refreshToken));



        authService.logout(request);



        verify(refreshTokenService)
                .delete(refreshToken);
    }



    @Test
    void register_should_create_user_when_data_is_valid() {

        RegisterRequest request =
                new RegisterRequest(
                        "new@test.com",
                        "password123",
                        "password123"
                );


        AuthUser user = createUser();


        when(authUserRepository.existsByEmail(request.email()))
                .thenReturn(false);


        when(authMapper.toEntity(
                request,
                "encodedPassword"
        ))
                .thenReturn(user);


        when(passwordEncoder.encode(request.password()))
                .thenReturn("encodedPassword");



        RegisterResponse response =
                authService.register(request);



        assertThat(response.message())
                .isEqualTo(
                        "Registration successful. Please verify your email."
                );


        verify(authUserRepository)
                .save(user);
        verify(emailVerificationService)
                .createToken(user);
    }







    @Test
    void register_should_throw_exception_when_email_already_exists() {

        RegisterRequest request =
                new RegisterRequest(
                        "test@test.com",
                        "password123",
                        "password123"
                );


        when(authUserRepository.existsByEmail(request.email()))
                .thenReturn(true);



        assertThatThrownBy(() ->
                authService.register(request)
        )
                .isInstanceOf(EmailAlreadyExistsException.class);



        verify(authUserRepository, never())
                .save(any());
    }





    @Test
    void register_should_throw_exception_when_password_confirmation_is_wrong() {

        RegisterRequest request =
                new RegisterRequest(
                        "test@test.com",
                        "password123",
                        "differentPassword"
                );


        when(authUserRepository.existsByEmail(request.email()))
                .thenReturn(false);



        assertThatThrownBy(() ->
                authService.register(request)
        )
                .isInstanceOf(PasswordMismatchException.class);



        verify(authUserRepository, never())
                .save(any());
    }


    @Test
    void login_should_fail_when_email_is_not_verified() {

        LoginRequest request =
                new LoginRequest(
                        "test@test.com",
                        "password123"
                );

        AuthUser user = createUser();
        user.setStatus(UserStatus.PENDING);


        when(authUserRepository.findByEmail(request.email()))
                .thenReturn(Optional.of(user));


        assertThatThrownBy(() ->
                authService.login(request)
        )
                .isInstanceOf(InvalidCredentialsException.class);


        verify(passwordEncoder, never())
                .matches(any(), any());
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