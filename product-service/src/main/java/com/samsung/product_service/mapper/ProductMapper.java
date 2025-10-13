package com.samsung.product_service.mapper;

import com.samsung.product_service.dto.response.ProductResponse;
import com.samsung.product_service.entity.Product;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toProductResponses(List<Product> products);
}
