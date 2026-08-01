package com.tekhoufeha.userservice.service.impl;


import com.tekhoufeha.userservice.dto.request.UpdateProfileRequest;
import com.tekhoufeha.userservice.dto.response.CloudinaryUploadResponse;
import com.tekhoufeha.userservice.dto.response.UserProfileResponse;
import com.tekhoufeha.userservice.entity.UserProfile;
import com.tekhoufeha.userservice.exception.UserProfileNotFoundException;
import com.tekhoufeha.userservice.mapper.UserProfileMapper;
import com.tekhoufeha.userservice.repository.UserProfileRepository;
import com.tekhoufeha.userservice.service.CloudinaryService;
import com.tekhoufeha.userservice.service.UserProfileService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileServiceImpl implements UserProfileService {


    private final UserProfileRepository userProfileRepository;

    private final UserProfileMapper userProfileMapper;

    private final CloudinaryService cloudinaryService;


    @Value("${app.default-avatar-url}")
    private String defaultAvatarUrl;



    @Override
    public UserProfileResponse initProfile(UUID authUserId) {


        UserProfile profile =
                userProfileRepository.findByAuthUserId(authUserId)
                        .orElseGet(() -> {

                            UserProfile newProfile =
                                    UserProfile.builder()
                                            .authUserId(authUserId)
                                            .avatarUrl(defaultAvatarUrl)
                                            .avatarPublicId(null)
                                            .profileCompleted(false)
                                            .build();


                            return userProfileRepository.save(newProfile);
                        });


        return userProfileMapper.toResponse(profile);
    }





    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(UUID authUserId) {


        UserProfile profile =
                userProfileRepository.findByAuthUserId(authUserId)
                        .orElseThrow(() ->
                                new UserProfileNotFoundException(
                                        "User profile not found"
                                )
                        );


        return userProfileMapper.toResponse(profile);
    }





    @Override
    public UserProfileResponse updateProfile(
            UUID authUserId,
            UpdateProfileRequest request) {


        UserProfile profile =
                userProfileRepository.findByAuthUserId(authUserId)
                        .orElseThrow(() ->
                                new UserProfileNotFoundException(
                                        "User profile not found"
                                )
                        );


        userProfileMapper.updateEntity(
                profile,
                request
        );


        profile.setProfileCompleted(
                isProfileCompleted(profile)
        );


        UserProfile updatedProfile =
                userProfileRepository.save(profile);


        return userProfileMapper.toResponse(updatedProfile);
    }





    @Override
    public UserProfileResponse updateAvatar(
            UUID authUserId,
            MultipartFile file) {


        UserProfile profile =
                userProfileRepository.findByAuthUserId(authUserId)
                        .orElseThrow(() ->
                                new UserProfileNotFoundException(
                                        "User profile not found"
                                )
                        );



        if (profile.getAvatarPublicId() != null) {

            cloudinaryService.deleteImage(
                    profile.getAvatarPublicId()
            );
        }



        CloudinaryUploadResponse upload =
                cloudinaryService.uploadImage(file);



        profile.setAvatarUrl(
                upload.url()
        );


        profile.setAvatarPublicId(
                upload.publicId()
        );



        UserProfile updatedProfile =
                userProfileRepository.save(profile);



        return userProfileMapper.toResponse(updatedProfile);
    }





    @Override
    public UserProfileResponse removeAvatar(
            UUID authUserId) {


        UserProfile profile =
                userProfileRepository.findByAuthUserId(authUserId)
                        .orElseThrow(() ->
                                new UserProfileNotFoundException(
                                        "User profile not found"
                                )
                        );



        if (profile.getAvatarPublicId() != null) {


            cloudinaryService.deleteImage(
                    profile.getAvatarPublicId()
            );
        }



        profile.setAvatarUrl(
                defaultAvatarUrl
        );


        profile.setAvatarPublicId(
                null
        );



        UserProfile updatedProfile =
                userProfileRepository.save(profile);



        return userProfileMapper.toResponse(updatedProfile);
    }







    private boolean isProfileCompleted(UserProfile profile) {

        return profile.getFirstName() != null
                && !profile.getFirstName().isBlank()

                && profile.getLastName() != null
                && !profile.getLastName().isBlank()

                && profile.getCity() != null
                && !profile.getCity().isBlank();
    }

}