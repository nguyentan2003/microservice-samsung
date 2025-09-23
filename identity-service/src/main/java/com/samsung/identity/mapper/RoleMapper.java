package com.samsung.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.samsung.identity.dto.request.RoleRequest;
import com.samsung.identity.dto.response.RoleResponse;
import com.samsung.identity.entity.Role;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

    Set<RoleResponse> toSetRoleResponse(Set<Role> roles);
}
