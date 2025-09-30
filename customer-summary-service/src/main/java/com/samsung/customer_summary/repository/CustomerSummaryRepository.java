package com.samsung.customer_summary.repository;


import com.samsung.customer_summary.entity.CustomerSummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerSummaryRepository extends MongoRepository<CustomerSummary, String> {
    Optional<CustomerSummary> findByOrderId(String orderId);
}

