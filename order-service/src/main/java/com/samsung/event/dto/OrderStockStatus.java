package com.samsung.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderStockStatus {
    String orderId;
    
    // true la da giu cho con false la bi huy
    Boolean status;

    String userId;
}
