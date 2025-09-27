package com.samsung.shipping_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShippingResponse {
    String id;
    String orderId;
    String address;
    String trackingNumber;
    String status;
    String shippingDate;
    String deliveryDate;
    String currentPosition;
}
