package com.samsung.profile_service.controller;

import com.samsung.profile_service.dto.request.ProfileCreationRequest;
import com.samsung.profile_service.dto.response.ApiResponse;
import com.samsung.profile_service.dto.response.UserProfileResponse;
import com.samsung.profile_service.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InternalUserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @PostMapping("/internal/create")
    ApiResponse<UserProfileResponse> createUserProfile(@RequestBody @Valid ProfileCreationRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.createProfile(request))
                .build();
    }



}
