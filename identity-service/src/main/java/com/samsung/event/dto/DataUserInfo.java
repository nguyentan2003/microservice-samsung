package com.samsung.event.dto;

import com.samsung.identity.dto.response.RoleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataUserInfo {
    String id;
    String orderId;
    String username;
    String fullName;
    String email;
    String phone;
    String address;

}
