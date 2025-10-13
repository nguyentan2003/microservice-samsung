package com.samsung.customer_summary.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        return repository.findAll();
    }

    public CustomerSummary getSummaryByOrderId(String orderId) {
        return repository
                .findByOrderId(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_SUMMARY_SERVICE_NOT_FOUND));
    }

    public List<CustomerSummary> getListSummaryByUserId(String userId) {
        return repository.findAll().stream()
                .filter(item -> item.getUserId().equals(userId))
                .sorted(Comparator.comparing(CustomerSummary::getOrderDate)
                        .reversed()) // sắp xếp giảm dần theo thời gian
                .collect(Collectors.toList());
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
