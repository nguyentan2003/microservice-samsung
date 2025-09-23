package com.samsung.profile_service.service;

import com.samsung.profile_service.dto.request.ProfileCreationRequest;
import com.samsung.profile_service.dto.response.UserProfileResponse;
import com.samsung.profile_service.entity.UserProfile;
import com.samsung.profile_service.exception.AppException;
import com.samsung.profile_service.exception.ErrorCode;
import com.samsung.profile_service.mapper.UserProfileMapper;
import com.samsung.profile_service.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserProfileService {
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    UserProfileMapper userProfileMapper;

    public UserProfileResponse createProfile(ProfileCreationRequest request){
        UserProfile userProfile = userProfileMapper.toUserProfile(request);

        return userProfileMapper.toUserProfileResponse(userProfileRepository.save(userProfile));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserProfileResponse> getListProfile() {
        // Lấy toàn bộ UserProfile từ DB
        List<UserProfile> entities = userProfileRepository.findAll();

        // Map sang DTO
        return userProfileMapper.toListUserProfileResponse(entities);
    }

    public UserProfileResponse getProfileByUserId(String userId) {

        UserProfile userProfile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userProfileMapper.toUserProfileResponse(userProfile);

    }


}
