package com.tekhoufeha.auth.dto.response;

import com.tekhoufeha.auth.entity.AuthProvider;
import com.tekhoufeha.auth.entity.Role;
import com.tekhoufeha.auth.entity.UserStatus;
import java.util.UUID;

public record UserProfileResponse(
        UUID id,

        String email,

        Role role,

        UserStatus status,

        AuthProvider provider
) {
}
