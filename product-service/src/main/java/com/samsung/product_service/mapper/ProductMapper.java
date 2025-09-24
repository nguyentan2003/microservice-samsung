package com.samsung.product_service.mapper;

import com.samsung.product_service.dto.request.ProductRequest;

import com.samsung.product_service.dto.response.ProductResponse;
import com.samsung.product_service.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toProductResponse(Product product);
    List<ProductResponse> toProductResponses(List<Product> products);
}
