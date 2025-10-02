package com.samsung.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataOrderCreated {
     String orderId;
     String userId;
     String orderDate;
     Long totalAmount;
     String paymentType;
     String status;
     String address;
     List<ItemDetail> listItemDetail;
}
