package com.samsung.order_service.dto.request;

import java.util.List;

import com.samsung.event.dto.ItemDetail;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationRequest {

    String userId;
    String orderDate;
    Long totalAmount;
    String paymentType;
    String address;
    List<ItemDetail> listItemDetail;
}
