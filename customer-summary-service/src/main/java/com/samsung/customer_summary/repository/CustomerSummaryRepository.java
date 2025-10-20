package com.samsung.customer_summary.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.samsung.customer_summary.entity.CustomerSummary;

@Repository
public interface CustomerSummaryRepository extends MongoRepository<CustomerSummary, String> {
    Optional<CustomerSummary> findByOrderId(String orderId);
    // 1️⃣ Lấy 100 bản ghi mới nhất (theo thời gian tạo)
    List<CustomerSummary> findTop100ByOrderByCreatedAtDesc();

    // 2️⃣ Lấy 100 bản ghi theo một điều kiện cụ thể, ví dụ theo userId
    List<CustomerSummary> findTop100ByUserIdOrderByCreatedAtDesc(String userId);
}
