package com.samsung.shipping_service.controller;

import com.samsung.shipping_service.dto.request.ShippingRequest;
import com.samsung.shipping_service.dto.response.ApiResponse;
import com.samsung.shipping_service.dto.response.ShippingResponse;
import com.samsung.shipping_service.service.ShippingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShippingController {
     ShippingService shippingService;


    @PostMapping("/create")
    public ApiResponse<ShippingResponse> createPayment(@RequestBody ShippingRequest request)  {

        return ApiResponse.<ShippingResponse>builder()
                .result(shippingService.createShipping(request))
                .build();
    }

    @GetMapping("/list")
    public ApiResponse<List<ShippingResponse>> getAllProducts() {
        return ApiResponse.<List<ShippingResponse>>builder()
                .result(shippingService.getListShipping())
                .build();

    }


}
