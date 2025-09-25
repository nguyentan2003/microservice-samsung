package com.samsung.payment_service.entity;

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
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Column(name = "order_id")
    String orderId;
    @Column(name = "payment_method")
    String paymentMethod;
    Long amount;
    String status;

    @Column(name = "transaction_id")
    String transactionId;

    @Column(name = "payment_time")
    String paymentTime;

}
