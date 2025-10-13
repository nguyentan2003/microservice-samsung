package com.samsung.customer_summary.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document("pending_messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PendingMessage {
    @Id
    private String id;

    private String type; // loại message (ORDER_PRODUCT_DATA, DATA_PAYMENT_SUCCESS,...)
    private Object payload;
    private String orderId;
    private int retryCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
