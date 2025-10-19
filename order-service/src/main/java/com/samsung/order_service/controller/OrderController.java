package com.samsung.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import com.samsung.data_static.OrderStatus;
import com.samsung.data_static.Topic;
import com.samsung.event.dto.UpdateOrderStatus;
import com.samsung.order_service.dto.request.OrderCreationRequest;
import com.samsung.order_service.dto.response.ApiResponse;
import com.samsung.order_service.dto.response.OrderResponse;
import com.samsung.order_service.entity.Order;
import com.samsung.order_service.exception.AppException;
import com.samsung.order_service.exception.ErrorCode;
import com.samsung.order_service.service.OrderService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/create")
    ApiResponse<OrderResponse> create(@RequestBody OrderCreationRequest request) {

        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }

    @PatchMapping("/cancel-order/{orderId}")
    public ApiResponse<Boolean> cancelOrder(@PathVariable String orderId) {
        Order order = orderService.cancelOrder(orderId);

        kafkaTemplate.send(
                Topic.UPDATE_ORDER_STATUS,
                orderId,
                UpdateOrderStatus.builder()
                        .userId(order.getUserId())
                        .orderId(order.getId())
                        .status(OrderStatus.CANCELED)
                        .build());
        return ApiResponse.<Boolean>builder().result(true).build();
    }

    @PatchMapping("/update-status/{orderId}")
    public ApiResponse<Boolean> updateOrderStatus(@PathVariable String orderId, @RequestParam("status") String status) {
        if (status.equals(OrderStatus.SHIPPING) || status.equals(OrderStatus.CANCELED)) {
            Order order = orderService.updateOrderStatusAdmin(orderId, status);
            kafkaTemplate.send(
                    Topic.UPDATE_ORDER_STATUS,
                    orderId,
                    UpdateOrderStatus.builder()
                            .userId(order.getUserId())
                            .orderId(order.getId())
                            .status(status)
                            .build());

            return ApiResponse.<Boolean>builder().result(true).build();
        } else throw new AppException(ErrorCode.STATUS_NOT_MATCH);
    }
}
