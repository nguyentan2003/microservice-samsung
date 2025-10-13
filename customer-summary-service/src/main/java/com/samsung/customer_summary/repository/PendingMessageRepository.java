package com.samsung.customer_summary.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.samsung.customer_summary.entity.PendingMessage;

public interface PendingMessageRepository extends MongoRepository<PendingMessage, String> {
    List<PendingMessage> findByOrderId(String orderId);
}
