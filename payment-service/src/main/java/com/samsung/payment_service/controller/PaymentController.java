package com.samsung.payment_service.controller;

import com.samsung.payment_service.dto.request.PaymentRequest;
import com.samsung.payment_service.dto.response.ApiResponse;
import com.samsung.payment_service.dto.response.PaymentResponse;
import com.samsung.payment_service.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentController {
     PaymentService paymentService;


    @PostMapping("/create")
    public ApiResponse<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest)  {

        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.createPayment(paymentRequest))
                .build();
    }

    @GetMapping("/list")
    public ApiResponse<List<PaymentResponse>> getAllProducts() {
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getListPayment())
                .build();

    }


}
