package com.tekhoufeha.userservice.controller;


import com.tekhoufeha.userservice.dto.request.UpdateProfileRequest;
import com.tekhoufeha.userservice.dto.response.UserProfileResponse;
import com.tekhoufeha.userservice.service.UserProfileService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserProfileService userProfileService;



    @PostMapping("/profile/init")
    public ResponseEntity<UserProfileResponse> initProfile(
            @RequestHeader("X-User-Id") UUID authUserId
    ) {


        return ResponseEntity.ok(
                userProfileService.initProfile(authUserId)
        );
    }





    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getProfile(
            @RequestHeader("X-User-Id") UUID authUserId
    ) {


        return ResponseEntity.ok(
                userProfileService.getProfile(authUserId)
        );
    }





    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateProfile(
            @RequestHeader("X-User-Id") UUID authUserId,

            @Valid
            @RequestBody UpdateProfileRequest request
    ) {


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
            @RequestHeader("X-User-Id") UUID authUserId,
            @RequestParam("file") MultipartFile file
    ) {


        return ResponseEntity.ok(
                userProfileService.updateAvatar(
                        authUserId,
                        file
                )
        );
    }



    @DeleteMapping("/me/avatar")
    public ResponseEntity<UserProfileResponse> removeAvatar(
            @RequestHeader("X-User-Id") UUID authUserId
    ) {


        return ResponseEntity.ok(
                userProfileService.removeAvatar(authUserId)
        );
    }

}