package com.tekhoufeha.auth.dto.response;

import lombok.Builder;

@Builder
public record RefreshTokenResponse(

        String accessToken,
        String refreshToken,
        String tokenType

) {
}