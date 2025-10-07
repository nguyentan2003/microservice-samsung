package com.samsung.customer_summary.service;

import com.samsung.customer_summary.entity.CustomerSummary;
import com.samsung.customer_summary.exception.AppException;
import com.samsung.customer_summary.exception.ErrorCode;
import com.samsung.customer_summary.repository.CustomerSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerSummaryService {

    private final CustomerSummaryRepository repository;

    public List<CustomerSummary> getAllSummaries() {
        return repository.findAll();
    }

    public CustomerSummary getSummaryByOrderId(String orderId) {
        return repository.findByOrderId(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_SUMMARY_SERVICE_NOT_FOUND));
    }

    public List<CustomerSummary> getListSummaryByUserId(String orderId) {
        List<CustomerSummary> list = new ArrayList<>();
        repository.findAll().forEach(item->{
            if(orderId.equals(item.getUserId())){
                list.add(item);
            }
        });
        return list;
    }

    public void deleteAll(){
        repository.deleteAll();
    }

}
