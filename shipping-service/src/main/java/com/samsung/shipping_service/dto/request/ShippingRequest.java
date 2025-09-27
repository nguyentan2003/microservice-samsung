package com.samsung.shipping_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShippingRequest {

    String orderId;
    String address;
    String trackingNumber;
    String status;
    String shippingDate;
    String deliveryDate;
    String currentPosition;
}
