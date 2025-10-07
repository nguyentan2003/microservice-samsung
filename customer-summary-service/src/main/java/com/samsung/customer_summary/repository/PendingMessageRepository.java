package com.samsung.customer_summary.repository;

import com.samsung.customer_summary.entity.PendingMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PendingMessageRepository extends MongoRepository<PendingMessage, String> {
    List<PendingMessage> findByOrderId(String orderId);
}
