package com.tekhoufeha.auth.service;


import com.tekhoufeha.auth.entity.AuthUser;
import com.tekhoufeha.auth.entity.Role;
import com.tekhoufeha.auth.entity.UserStatus;

import com.tekhoufeha.auth.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;


class JwtServiceTest {


    private JwtService jwtService;


    private final String secret =
            "4f6c8d0e5a1b9c7d3e2f8a6b1c9d4e7f5a8b2c6d9e1f3a7b4c8d0e5f1a9b3c7";


    @BeforeEach
    void setUp() throws Exception {

        jwtService = new JwtService();


        Field secretField =
                JwtService.class.getDeclaredField("secretKey");

        secretField.setAccessible(true);
        secretField.set(jwtService, secret);


        Field expirationField =
                JwtService.class.getDeclaredField("jwtExpiration");

        expirationField.setAccessible(true);
        expirationField.set(jwtService, 86400000L);
    }




    @Test
    void generateToken_should_create_valid_jwt_token() {


        AuthUser user = createUser();


        String token =
                jwtService.generateToken(user);



        assertThat(token)
                .isNotNull()
                .isNotEmpty();


        assertThat(jwtService.extractUsername(token))
                .isEqualTo("test@test.com");
    }





    @Test
    void extractUsername_should_return_user_email() {


        AuthUser user = createUser();


        String token =
                jwtService.generateToken(user);



        String username =
                jwtService.extractUsername(token);



        assertThat(username)
                .isEqualTo(user.getEmail());
    }





    @Test
    void isTokenValid_should_return_true_when_token_and_user_match() {


        AuthUser user = createUser();


        String token =
                jwtService.generateToken(user);



        UserDetails userDetails =
                User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles("USER")
                        .build();



        boolean result =
                jwtService.isTokenValid(
                        token,
                        userDetails
                );



        assertThat(result)
                .isTrue();
    }





    @Test
    void isTokenValid_should_return_false_when_username_different() {


        AuthUser user = createUser();


        String token =
                jwtService.generateToken(user);



        UserDetails userDetails =
                User.builder()
                        .username("other@test.com")
                        .password("password")
                        .roles("USER")
                        .build();



        boolean result =
                jwtService.isTokenValid(
                        token,
                        userDetails
                );



        assertThat(result)
                .isFalse();
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