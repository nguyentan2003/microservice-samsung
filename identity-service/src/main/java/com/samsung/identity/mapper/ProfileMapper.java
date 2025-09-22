package com.samsung.identity.mapper;

import org.mapstruct.Mapper;

import com.samsung.identity.dto.request.ProfileCreationRequest;
import com.samsung.identity.dto.request.UserCreationRequest;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    ProfileCreationRequest toProfileCreationRequest(UserCreationRequest request);
}
