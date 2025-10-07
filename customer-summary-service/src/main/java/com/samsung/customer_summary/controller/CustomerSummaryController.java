package com.samsung.customer_summary.controller;

import com.samsung.customer_summary.dto.ApiResponse;
import com.samsung.customer_summary.entity.CustomerSummary;
import com.samsung.customer_summary.service.CustomerSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerSummaryController {

    private final CustomerSummaryService service;

    // Lấy toàn bộ summary
    @GetMapping("get-all")
    public ApiResponse<List<CustomerSummary>> getAllSummaries() {
        return ApiResponse.<List<CustomerSummary>>builder()
                .result(service.getAllSummaries())
                .build();
    }

    // Lấy summary theo orderId
    @GetMapping("/get-by-orderId/{orderId}")
    public ApiResponse<CustomerSummary> getSummaryByOrderId(@PathVariable String orderId) {
        return ApiResponse.<CustomerSummary>builder()
                .result(service.getSummaryByOrderId(orderId))
                .build();
    }

    @GetMapping("/get-list-order-user/{userId}")
    public ApiResponse<List<CustomerSummary>> getListSummaryByUserId(@PathVariable String userId) {
        return ApiResponse.<List<CustomerSummary>>builder()
                .result(service.getListSummaryByUserId(userId))
                .build();
    }
    @DeleteMapping("/deleteAll")
    public void deleteAll(){
        service.deleteAll();
    }
}
