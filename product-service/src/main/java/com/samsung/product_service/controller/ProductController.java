package com.samsung.product_service.controller;

import com.samsung.product_service.dto.request.ProductRequest;
import com.samsung.product_service.dto.response.ApiResponse;
import com.samsung.product_service.dto.response.ProductResponse;
import com.samsung.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ApiResponse<ProductResponse> createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("type") String type,
            @RequestParam("price") Long price,
            @RequestParam("stockQuantity") Long stockQuantity,
            @RequestParam("image") MultipartFile image
    ) throws IOException {
        ProductRequest request = ProductRequest.builder()
                .name(name)
                .description(description)
                .type(type)
                .price(price)
                .stockQuantity(stockQuantity)
                .image(image)
                .build();

        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }

    @GetMapping("/list")
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProducts())
                .build();

    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable String id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(id))
                .build();

    }
}
