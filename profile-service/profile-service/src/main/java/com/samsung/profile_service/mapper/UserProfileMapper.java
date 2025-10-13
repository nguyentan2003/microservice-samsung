package com.samsung.profile_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.samsung.profile_service.dto.request.ProfileCreationRequest;
import com.samsung.profile_service.dto.response.UserProfileResponse;
import com.samsung.profile_service.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile userProfile);

    List<UserProfileResponse> toListUserProfileResponse(List<UserProfile> entities);
}
