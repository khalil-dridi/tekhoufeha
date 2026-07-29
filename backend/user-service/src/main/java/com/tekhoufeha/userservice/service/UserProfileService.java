package com.tekhoufeha.userservice.service;

import com.tekhoufeha.userservice.dto.request.UpdateProfileRequest;
import com.tekhoufeha.userservice.dto.response.UserProfileResponse;

import java.util.UUID;


public interface UserProfileService {


    UserProfileResponse initProfile(UUID authUserId);


    UserProfileResponse getProfile(UUID authUserId);


    UserProfileResponse updateProfile(
            UUID authUserId,
            UpdateProfileRequest request
    );

}