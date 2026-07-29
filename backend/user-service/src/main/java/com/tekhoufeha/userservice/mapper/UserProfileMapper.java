package com.tekhoufeha.userservice.mapper;


import com.tekhoufeha.userservice.dto.request.UpdateProfileRequest;
import com.tekhoufeha.userservice.dto.response.UserProfileResponse;
import com.tekhoufeha.userservice.entity.UserProfile;
import org.springframework.stereotype.Component;


@Component
public class UserProfileMapper {


    public UserProfileResponse toResponse(
            UserProfile userProfile) {


        return new UserProfileResponse(

                userProfile.getId(),

                userProfile.getAuthUserId(),

                userProfile.getFirstName(),

                userProfile.getLastName(),

                userProfile.getPhone(),

                userProfile.getCity(),

                userProfile.getGovernorate(),

                userProfile.getAvatarUrl(),

                userProfile.getBio(),

                userProfile.isProfileCompleted()

        );
    }



    public void updateEntity(
            UserProfile userProfile,
            UpdateProfileRequest request) {


        userProfile.setFirstName(
                request.firstName()
        );


        userProfile.setLastName(
                request.lastName()
        );


        userProfile.setPhone(
                request.phone()
        );


        userProfile.setCity(
                request.city()
        );


        userProfile.setGovernorate(
                request.governorate()
        );


        userProfile.setBio(
                request.bio()
        );

    }

}