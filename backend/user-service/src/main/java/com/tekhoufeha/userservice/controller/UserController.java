package com.tekhoufeha.userservice.controller;


import com.tekhoufeha.userservice.dto.request.UpdateProfileRequest;
import com.tekhoufeha.userservice.dto.response.UserProfileResponse;
import com.tekhoufeha.userservice.service.UserProfileService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.UUID;



@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserProfileService userProfileService;



    @PostMapping("/profile/init")
    public ResponseEntity<UserProfileResponse> initProfile(
            Authentication authentication
    ) {


        UUID authUserId =
                UUID.fromString(
                        authentication.getName()
                );


        return ResponseEntity.ok(
                userProfileService.initProfile(authUserId)
        );
    }





    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getProfile(
            Authentication authentication
    ) {


        UUID authUserId =
                UUID.fromString(
                        authentication.getName()
                );


        return ResponseEntity.ok(
                userProfileService.getProfile(authUserId)
        );
    }






    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateProfile(
            Authentication authentication,

            @Valid
            @RequestBody UpdateProfileRequest request
    ) {


        UUID authUserId =
                UUID.fromString(
                        authentication.getName()
                );


        return ResponseEntity.ok(
                userProfileService.updateProfile(
                        authUserId,
                        request
                )
        );
    }






    @PostMapping(
            value = "/me/avatar",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<UserProfileResponse> updateAvatar(
            Authentication authentication,
            @RequestParam("file") MultipartFile file
    ) {


        UUID authUserId =
                UUID.fromString(
                        authentication.getName()
                );


        return ResponseEntity.ok(
                userProfileService.updateAvatar(
                        authUserId,
                        file
                )
        );
    }






    @DeleteMapping("/me/avatar")
    public ResponseEntity<UserProfileResponse> removeAvatar(
            Authentication authentication
    ) {


        UUID authUserId =
                UUID.fromString(
                        authentication.getName()
                );


        return ResponseEntity.ok(
                userProfileService.removeAvatar(authUserId)
        );
    }

}