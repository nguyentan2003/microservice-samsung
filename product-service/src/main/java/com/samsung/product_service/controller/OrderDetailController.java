package com.samsung.product_service.controller;

import com.samsung.product_service.dto.request.OrderDetailCreationRequest;
import com.samsung.product_service.dto.response.ApiResponse;
import com.samsung.product_service.dto.response.OrderDetailResponse;
import com.samsung.product_service.service.OrderDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order-detail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderDetailController {

    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/list")
    ApiResponse<List<OrderDetailResponse>> getListOrder() {
        return ApiResponse.<List<OrderDetailResponse>>builder()
                .result(orderDetailService.getListOrderDetail())
                .build();
    }

    @GetMapping("/{orderDetailId}")
    ApiResponse<OrderDetailResponse> getOrderByOrderId(@PathVariable String orderDetailId) {
        return ApiResponse.<OrderDetailResponse>builder()
                .result(orderDetailService.getOrderByOrderIdDetail(orderDetailId))
                .build();
    }
    @PostMapping("/create")
    ApiResponse<OrderDetailResponse> create(@RequestBody OrderDetailCreationRequest request) {

        return ApiResponse.<OrderDetailResponse>builder()
                .result(orderDetailService.createOrderDetail(request))
                .build();
    }

}
