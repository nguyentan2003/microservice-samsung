package com.samsung.profile_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.samsung.profile_service.dto.response.ApiResponse;
import com.samsung.profile_service.dto.response.UserProfileResponse;
import com.samsung.profile_service.service.UserProfileService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @GetMapping("/user-profile/get-list")
    ApiResponse<List<UserProfileResponse>> getListProfile() {
        return ApiResponse.<List<UserProfileResponse>>builder()
                .result(userProfileService.getListProfile())
                .build();
    }

    @GetMapping("/user-profile/get-by-user-id/{userId}")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String userId) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.getProfileByUserId(userId))
                .build();
    }
}
