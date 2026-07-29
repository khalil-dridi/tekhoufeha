package com.tekhoufeha.auth.config;

import com.tekhoufeha.auth.security.CustomUserDetailsService;
import com.tekhoufeha.auth.security.JwtAuthenticationEntryPoint;
import com.tekhoufeha.auth.security.JwtAuthenticationFilter;

import com.tekhoufeha.auth.security.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final CustomUserDetailsService customUserDetailsService;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }



    @Bean
    CommandLineRunner generatePassword(
            PasswordEncoder passwordEncoder) {

        return args -> {

            System.out.println("=======================================");
            System.out.println(
                    passwordEncoder.encode("admin123")
            );
            System.out.println("=======================================");

        };
    }




    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {


        http

                .csrf(csrf -> csrf.disable())


                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.IF_REQUIRED
                        )
                )


                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(
                                jwtAuthenticationEntryPoint
                        )
                )


                .authorizeHttpRequests(auth -> auth

                        // Routes publiques
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login",
                                "/api/auth/refresh-token",
                                "/api/auth/verify-email",
                                "/api/auth/forgot-password",
                                "/api/auth/reset-password",
                                "/oauth2/**",
                                "/login/**"
                        )
                        .permitAll()


                        // Routes protégées
                        .requestMatchers(
                                "/api/auth/me",
                                "/api/auth/change-password"
                        )
                        .authenticated()


                        .anyRequest()
                        .authenticated()
                )


                // Google OAuth2 Login
                .oauth2Login(oauth2 ->
                        oauth2.successHandler(
                                oAuth2AuthenticationSuccessHandler
                        )
                )



                .authenticationProvider(
                        authenticationProvider()
                )


                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();
    }





    @Bean
    public AuthenticationProvider authenticationProvider() {


        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider();


        authProvider.setUserDetailsService(
                customUserDetailsService
        );


        authProvider.setPasswordEncoder(
                passwordEncoder()
        );


        return authProvider;
    }





    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {


        return configuration.getAuthenticationManager();
    }

}