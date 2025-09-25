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
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;
    String description;
    String type;
    Long price;

    @Column(name = "stock_quantity")
    Long stockQuantity;

    @Column(name = "reserved_stock")
    Long reservedStock ;

    @Column(name = "image_url")
    String imageUrl;


}
