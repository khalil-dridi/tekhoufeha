package com.tekhoufeha.auth.mapper;

import com.tekhoufeha.auth.dto.request.RegisterRequest;
import com.tekhoufeha.auth.entity.AuthUser;
import com.tekhoufeha.auth.entity.Role;
import com.tekhoufeha.auth.entity.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public AuthUser toEntity(RegisterRequest request, String encodedPassword) {

        return AuthUser.builder()
                .email(request.email())
                .password(encodedPassword)
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
    }

}