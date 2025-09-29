package com.samsung.order_service.controller;

import com.samsung.order_service.dto.request.OrderCreationRequest;
import com.samsung.order_service.dto.response.ApiResponse;
import com.samsung.order_service.dto.response.OrderResponse;
import com.samsung.order_service.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

//    @GetMapping("/list")
//    ApiResponse<List<OrderResponse>> getListOrder() {
//        return ApiResponse.<List<OrderResponse>>builder()
//                .result(orderService.getListOrder())
//                .build();
//    }

//    @GetMapping("/{orderId}")
//    ApiResponse<OrderResponse> getOrderByOrderId(@PathVariable String orderId) {
//        return ApiResponse.<OrderResponse>builder()
//                .result(orderService.getOrderByOrderId(orderId))
//                .build();
//    }
    @PostMapping("/create")
    ApiResponse<OrderResponse> create(@RequestBody OrderCreationRequest request) {

        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(request))
                .build();
    }

}
