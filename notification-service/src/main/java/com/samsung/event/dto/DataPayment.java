package com.samsung.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataPayment {
    String id;
    String orderId;
    String paymentMethod;
    Long amount;
    String status;
    String transactionId;
    String paymentTime;
}
