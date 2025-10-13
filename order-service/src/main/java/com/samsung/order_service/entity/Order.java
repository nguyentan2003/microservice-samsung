package com.samsung.order_service.entity;

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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "user_id")
    String userId;

    @Column(name = "order_date")
    String orderDate;

    String status;

    @Column(name = "total_amount")
    Long totalAmount;

    //    PREPAID, POSTPAID
    @Column(name = "payment_type")
    String paymentType;

    String address;
}
