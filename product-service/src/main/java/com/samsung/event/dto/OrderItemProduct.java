package com.samsung.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemProduct {
    String productId;
    String productName;   // lưu snapshot tại thời điểm đặt
    Long priceAtTime;
    int quantity;
    String imageUrl;
}
