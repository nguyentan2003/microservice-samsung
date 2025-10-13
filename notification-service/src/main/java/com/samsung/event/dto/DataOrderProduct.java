package com.samsung.event.dto;

import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataOrderProduct {
    String orderId;
    List<OrderItemProduct> orderItemProducts;
}
