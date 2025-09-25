package com.samsung.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreatedEvent {
     String orderId;
     String userId;
     List<ItemDetail> listItemDetail;
     Long totalAmount;
}
