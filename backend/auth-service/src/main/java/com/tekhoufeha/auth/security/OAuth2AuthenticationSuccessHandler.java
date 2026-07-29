package com.tekhoufeha.auth.security;


import com.tekhoufeha.auth.entity.AuthProvider;
import com.tekhoufeha.auth.entity.AuthUser;
import com.tekhoufeha.auth.entity.RefreshToken;
import com.tekhoufeha.auth.entity.Role;
import com.tekhoufeha.auth.entity.UserStatus;
import com.tekhoufeha.auth.repository.AuthUserRepository;
import com.tekhoufeha.auth.service.RefreshTokenService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;


@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {


    private final AuthUserRepository authUserRepository;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;



    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {


        OAuth2User oauthUser =
                (OAuth2User) authentication.getPrincipal();


        String email =
                oauthUser.getAttribute("email");


        System.out.println(
                "Google email : " + email
        );



        AuthUser authUser =
                authUserRepository.findByEmail(email)
                        .orElseGet(() -> {


                            AuthUser newUser =
                                    AuthUser.builder()
                                            .email(email)
                                            .password(null)
                                            .provider(AuthProvider.GOOGLE)
                                            .role(Role.USER)
                                            .status(UserStatus.ACTIVE)
                                            .build();


                            return authUserRepository.save(newUser);

                        });



        String accessToken =
                jwtService.generateToken(authUser);



        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(authUser);



        System.out.println(
                "JWT generated : " + accessToken
        );


        System.out.println(
                "Refresh token : " + refreshToken.getToken()
        );



        response.sendRedirect(
                "http://localhost:4200/oauth-success?token="
                        + accessToken
                        + "&refreshToken="
                        + refreshToken.getToken()
        );

    }
}