package com.samsung.payment_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    String id;
    String orderId;
    String paymentMethod;
    Long amount;
    String status;
    String transactionId;
    String paymentTime;
}
