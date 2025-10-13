package com.samsung.order_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.samsung.event.dto.DataOrder;
import com.samsung.event.dto.DataOrderCreated;
import com.samsung.order_service.dto.request.OrderCreationRequest;
import com.samsung.order_service.dto.response.OrderResponse;
import com.samsung.order_service.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderCreationRequest request);

    DataOrderCreated toDataOrderCreated(OrderCreationRequest request);

    DataOrder toDataOrder(Order order);

    OrderResponse toOrderResponse(Order order);

    List<OrderResponse> toListOrderResponse(List<Order> orders);
}
