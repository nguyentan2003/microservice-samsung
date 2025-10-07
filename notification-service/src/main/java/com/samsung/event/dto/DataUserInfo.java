package com.samsung.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
