package com.samsung.product_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "order_id")
    String orderId;

    @Column(name = "product_id")
    String productId;

    int quantity;

    // gia tai thoi diem mua
    @Column(name = "price_at_time")
    Long priceAtTime;

    String status;
}
