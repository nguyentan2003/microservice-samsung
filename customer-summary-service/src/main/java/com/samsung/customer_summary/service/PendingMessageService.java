package com.samsung.customer_summary.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.samsung.customer_summary.entity.PendingMessage;
import com.samsung.customer_summary.repository.PendingMessageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PendingMessageService {
    private final PendingMessageRepository repo;

    public void savePending(String type, Object payload, String orderId) {
        PendingMessage msg = PendingMessage.builder()
                .type(type)
                .payload(payload)
                .orderId(orderId)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        repo.save(msg);
        log.warn("⚠️ Lưu message {} vào pending queue cho orderId={}", type, orderId);
    }
}
