package com.samsung.order_service.dto.request;

import com.samsung.event.dto.ItemDetail;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderCreationRequest {

    String userId;
    String orderDate;
    String status;
    Long totalAmount;
    List<ItemDetail> listItemDetail ;
}
