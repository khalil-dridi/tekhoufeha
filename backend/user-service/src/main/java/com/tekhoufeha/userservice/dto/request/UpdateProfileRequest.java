package com.tekhoufeha.userservice.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public record UpdateProfileRequest(


        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name must not exceed 50 characters")
        String firstName,



        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name must not exceed 50 characters")
        String lastName,



        @Size(max = 20, message = "Phone must not exceed 20 characters")
        String phone,



        @NotBlank(message = "City is required")
        @Size(max = 50, message = "City must not exceed 50 characters")
        String city,



        @Size(max = 50, message = "Governorate must not exceed 50 characters")
        String governorate,



        @Size(max = 500, message = "Bio must not exceed 500 characters")
        String bio


) {
}