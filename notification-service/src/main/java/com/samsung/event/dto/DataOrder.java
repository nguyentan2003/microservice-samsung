package com.samsung.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataOrder {

    String id;

    String userId;

    String orderDate;

    String status;

    Long totalAmount;

    String paymentType;
    String address;
}
