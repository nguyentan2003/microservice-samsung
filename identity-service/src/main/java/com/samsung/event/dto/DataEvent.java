package com.samsung.event.dto;

import com.samsung.identity.dto.response.RoleResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataEvent {
    String id;
    String username;
    String fullName;
    String email;
    String phone;
    String address;
}
