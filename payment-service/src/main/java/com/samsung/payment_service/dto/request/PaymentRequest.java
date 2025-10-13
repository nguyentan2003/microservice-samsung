package com.samsung.payment_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {

    String orderId;
    String paymentMethod;
    Long amount;
    String status;
    String transactionId;
    String paymentTime;
}
