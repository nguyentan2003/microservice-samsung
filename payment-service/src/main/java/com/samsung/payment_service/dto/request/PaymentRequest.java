package com.samsung.payment_service.dto.request;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

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
