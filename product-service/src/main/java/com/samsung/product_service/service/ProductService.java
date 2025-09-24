package com.samsung.product_service.service;

import com.samsung.product_service.dto.request.ProductRequest;
import com.samsung.product_service.dto.response.ProductResponse;
import com.samsung.product_service.entity.Product;
import com.samsung.product_service.exception.AppException;
import com.samsung.product_service.exception.ErrorCode;
import com.samsung.product_service.mapper.ProductMapper;
import com.samsung.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private static final String UPLOAD_DIR = "uploads/";

    public ProductResponse createProduct(ProductRequest request) throws IOException {
        // Tạo thư mục nếu chưa có
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        if(productRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.NAME_PRODUCT_EXISTED);
        }

        // Đặt tên file tránh trùng
        MultipartFile imageFile = request.getImage();
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.write(filePath, imageFile.getBytes());

        // Lưu DB
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .imageUrl(fileName)
                .build();

        productRepository.save(product);

        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productMapper.toProductResponses(productRepository.findAll());
    }

    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        return productMapper.toProductResponse(product);
    }
}
