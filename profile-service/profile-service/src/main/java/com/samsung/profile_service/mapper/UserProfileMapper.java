package com.samsung.profile_service.mapper;

import com.samsung.profile_service.dto.request.ProfileCreationRequest;
import com.samsung.profile_service.dto.response.UserProfileResponse;
import com.samsung.profile_service.entity.UserProfile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfile toUserProfile(ProfileCreationRequest request);

    UserProfileResponse toUserProfileResponse(UserProfile userProfile);

    List<UserProfileResponse> toListUserProfileResponse(List<UserProfile> entities);
}
