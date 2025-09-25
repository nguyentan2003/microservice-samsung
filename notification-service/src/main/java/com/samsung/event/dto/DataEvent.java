package com.samsung.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
