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
@RequestMapping("/user-profile")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @PostMapping("/create")
    ApiResponse<UserProfileResponse> createUserProfile(@RequestBody @Valid ProfileCreationRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(userProfileService.createProfile(request))
                .build();
    }

    @GetMapping("/get-list")
    ApiResponse<List<UserProfileResponse>> getListProfile() {
        return ApiResponse.<List<UserProfileResponse>>builder()
                .result(userProfileService.getListProfile())
                .build();
    }


//
//    @DeleteMapping("/{userId}")
//    ApiResponse<String> deleteUser(@PathVariable String userId) {
//        userService.deleteUser(userId);
//        return ApiResponse.<String>builder().result("User has been deleted").build();
//    }
//
//    @PutMapping("/{userId}")
//    ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
//        return ApiResponse.<UserResponse>builder()
//                .result(userService.updateUser(userId, request))
//                .build();
//    }

}
