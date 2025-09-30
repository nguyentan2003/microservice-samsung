package com.samsung.identity.mapper;

import com.samsung.event.dto.DataUserInfo;
import com.samsung.identity.dto.response.UserProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.samsung.identity.dto.request.UserCreationRequest;
import com.samsung.identity.dto.request.UserUpdateRequest;
import com.samsung.identity.dto.response.UserResponse;
import com.samsung.identity.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    DataUserInfo toDataUserInfo(UserResponse userResponse);
    UserResponse toUserResponse(User user);

    UserResponse toUserResponseFromRequest(UserCreationRequest userCreationRequest);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
