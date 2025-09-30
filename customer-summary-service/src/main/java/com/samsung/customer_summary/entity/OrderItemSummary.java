package com.samsung.customer_summary.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemSummary {
    String productId;
    String productName;   // lưu snapshot tại thời điểm đặt
    Long priceAtTime;
    int quantity;
    String imageUrl;
}
