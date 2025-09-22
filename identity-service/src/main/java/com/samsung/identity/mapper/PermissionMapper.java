package com.samsung.identity.mapper;

import org.mapstruct.Mapper;

import com.samsung.identity.dto.request.PermissionRequest;
import com.samsung.identity.dto.response.PermissionResponse;
import com.samsung.identity.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
