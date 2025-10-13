package com.samsung.customer_summary.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.samsung.customer_summary.entity.CustomerSummary;

@Repository
public interface CustomerSummaryRepository extends MongoRepository<CustomerSummary, String> {
    Optional<CustomerSummary> findByOrderId(String orderId);
}
