package com.samsung.order_service.mapper;

import com.samsung.event.dto.DataOrder;
import com.samsung.order_service.dto.request.OrderCreationRequest;

import com.samsung.order_service.dto.response.OrderResponse;
import com.samsung.order_service.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderCreationRequest request);
    DataOrder toDataOrder(Order order);
    OrderResponse toOrderResponse(Order order);
    List<OrderResponse> toListOrderResponse(List<Order> orders);

}
