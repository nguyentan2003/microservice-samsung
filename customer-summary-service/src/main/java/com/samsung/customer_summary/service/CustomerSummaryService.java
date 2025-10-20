package com.samsung.customer_summary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.samsung.customer_summary.entity.CustomerSummary;
import com.samsung.customer_summary.exception.AppException;
import com.samsung.customer_summary.exception.ErrorCode;
import com.samsung.customer_summary.repository.CustomerSummaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerSummaryService {

    private final CustomerSummaryRepository repository;

    public List<CustomerSummary> getAllSummaries() {
        return repository.findTop100ByOrderByCreatedAtDesc();
    }

    public CustomerSummary getSummaryByOrderId(String orderId) {
        return repository
                .findByOrderId(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_SUMMARY_SERVICE_NOT_FOUND));
    }

    public List<CustomerSummary> getListSummaryByUserId(String userId) {
        return repository.findTop100ByUserIdOrderByCreatedAtDesc(userId);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
