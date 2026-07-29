package com.tekhoufeha.userservice.dto.response;


import java.util.UUID;


public record UserProfileResponse(

        UUID id,

        UUID authUserId,

        String firstName,

        String lastName,

        String phone,

        String city,

        String governorate,

        String avatarUrl,

        String bio,

        boolean profileCompleted

) {
}