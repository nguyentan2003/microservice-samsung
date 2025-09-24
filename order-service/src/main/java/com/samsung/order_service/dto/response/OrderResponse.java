package com.samsung.order_service.dto.response;

import com.samsung.order_service.entity.OrderDetail;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String id;
    String userId;
    String orderDate;
    String status;
    Long totalAmount;
    List<OrderDetailResponse> listOrderDetailResponse;
}
