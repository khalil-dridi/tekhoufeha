package com.tekhoufeha.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;


public record UpdateProfileRequest(


        @NotBlank(message = "First name is required")
        String firstName,


        @NotBlank(message = "Last name is required")
        String lastName,


        String phone,


        @NotBlank(message = "City is required")
        String city,


        String governorate,


        String bio


) {
}