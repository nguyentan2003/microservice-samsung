package com.samsung.order_service.mapper;

import com.samsung.order_service.dto.request.OrderCreationRequest;

import com.samsung.order_service.dto.response.OrderResponse;
import com.samsung.order_service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderCreationRequest request);

    OrderResponse toOrderResponse(Order order);
    List<OrderResponse> toListOrderResponse(List<Order> orders);

}
