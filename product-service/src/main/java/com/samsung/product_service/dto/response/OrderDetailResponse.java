package com.samsung.product_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {

    String id;
    String orderId;
    String productId;
    int quantity;
    Long priceAtTime;
}
