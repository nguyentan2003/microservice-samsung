package com.samsung.shipping_service.entity;

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
@Table(name = "shipping")
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "order_id")
    String orderId;

    String address;
    @Column(name = "tracking_number")
    String trackingNumber;

    String status;

    @Column(name = "shipping_date")
    String shippingDate;

    @Column(name = "delivery_date")
    String deliveryDate;

}
