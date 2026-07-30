package com.tekhoufeha.userservice.service;


import com.tekhoufeha.userservice.dto.request.UpdateProfileRequest;
import com.tekhoufeha.userservice.dto.response.UserProfileResponse;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;



public interface UserProfileService {


    UserProfileResponse initProfile(UUID authUserId);



    UserProfileResponse getProfile(UUID authUserId);



    UserProfileResponse updateProfile(
            UUID authUserId,
            UpdateProfileRequest request
    );



    /**
     * Upload or replace user avatar
     */
    UserProfileResponse updateAvatar(
            UUID authUserId,
            MultipartFile file
    );



    /**
     * Remove user avatar and restore default avatar
     */
    UserProfileResponse removeAvatar(
            UUID authUserId
    );

}