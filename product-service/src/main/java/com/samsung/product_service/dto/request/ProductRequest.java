package com.samsung.product_service.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {

    String name;
    String description;
    String type;
    Long price;
    Long stockQuantity;
    MultipartFile image; // upload file
}
