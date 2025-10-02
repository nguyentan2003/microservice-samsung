package com.samsung.customer_summary.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "customer-summary")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerSummary {

    @Id
    String id;

    // Profile
    String userId;
    String username;
    String email;
    String fullName;
    String phone;
    String address;

    // Order
    String orderId;
    String orderDate;
    Long totalAmount;
    String paymentType;
    String orderStatus;
    String shippingAddress;
    // Payment
    String paymentMethod;
    String paymentStatus;
    String transactionId;
    String paymentTime;

    // Danh sách sản phẩm đã chọn trong order
    String statusStock;
    List<OrderItemSummary> orderItemSummaries;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}

