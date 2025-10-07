package com.samsung.product_service.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String id;
    String name;
    String description;
    String type;
    Long price;
    Long stockQuantity;
    Long reservedStock ;
    String imageUrl; // link ảnh hoặc tên file
}
