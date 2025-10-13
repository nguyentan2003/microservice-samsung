package com.samsung.product_service.service;

import com.samsung.event.dto.DataOrderCreated;
import com.samsung.event.dto.ItemDetail;
import com.samsung.event.dto.OrderItemProduct;
import com.samsung.product_service.dto.request.OrderDetailCreationRequest;
import com.samsung.product_service.dto.request.ProductRequest;
import com.samsung.product_service.dto.response.ProductResponse;
import com.samsung.product_service.entity.OrderDetail;
import com.samsung.product_service.entity.Product;
import com.samsung.product_service.exception.AppException;
import com.samsung.product_service.exception.ErrorCode;
import com.samsung.product_service.mapper.ProductMapper;
import com.samsung.product_service.repository.OrderDetailRepository;
import com.samsung.product_service.repository.ProductRepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final OrderDetailService orderDetailService;
    private final OrderDetailRepository orderDetailRepository;

    @Value("${app.upload.dir}")
    private String uploadDir;
    // private static final String UPLOAD_DIR = "C:/java/Java Spring Boot/microservice-samsung/uploads/";

    public ProductResponse createProduct(ProductRequest request) throws IOException {
        // Tạo thư mục nếu chưa có
        File uploadDir = new File(this.uploadDir);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        if (productRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.NAME_PRODUCT_EXISTED);
        }

        // Đặt tên file tránh trùng
        MultipartFile imageFile = request.getImage();
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(this.uploadDir + fileName);
        Files.write(filePath, imageFile.getBytes());

        // Lưu DB
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .type(request.getType())
                .price(request.getPrice())
                .reservedStock(0L)
                .stockQuantity(request.getStockQuantity())
                .imageUrl(fileName)
                .build();

        productRepository.save(product);

        return productMapper.toProductResponse(product);
    }

    public ProductResponse updateProduct(String productId, ProductRequest request) throws IOException {
        // Lưu DB
        Product product = productRepository.findById(productId).orElseThrow(() -> {
            throw new AppException(ErrorCode.PRODUCT_NOT_EXISTED);
        });
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            // Đặt tên file tránh trùng
            MultipartFile imageFile = request.getImage();
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(this.uploadDir + fileName);
            Files.write(filePath, imageFile.getBytes());

            product.setImageUrl(fileName);
        }

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setType(request.getType());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());

        productRepository.save(product);

        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productMapper.toProductResponses(productRepository.findAll());
    }

    public ProductResponse getProductById(String id) {
        Product product =
                productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));
        return productMapper.toProductResponse(product);
    }

    @Transactional
    public boolean checkStock(DataOrderCreated orderCreatedEvent) {

        // Lấy tất cả productId trong đơn hàng
        List<String> productIds = orderCreatedEvent.getListItemDetail()
                .stream()
                .map(ItemDetail::getProductId)
                .toList();

        // Lấy toàn bộ product một lần
        List<Product> products = productRepository.findAllById(productIds);

        // Chuyển về map để dễ truy xuất
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        // 1️⃣ Kiểm tra tồn kho
        for (ItemDetail item : orderCreatedEvent.getListItemDetail()) {
            Product product = productMap.get(item.getProductId());

            if (product == null) {
                log.info ("Không tồn tại sản phẩm ID: " + item.getProductId());
                return false;
            }

            long available = product.getStockQuantity() - product.getReservedStock();
            if (available < item.getQuantity()) {
                log.info("Sản phẩm " + product.getName() + " không đủ hàng");
                return false;
            }
        }

        // 2️⃣ Nếu đủ hàng, cập nhật reservedStock + tạo OrderDetail
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (ItemDetail item : orderCreatedEvent.getListItemDetail()) {
            Product product = productMap.get(item.getProductId());
            product.setReservedStock(product.getReservedStock() + item.getQuantity());

            orderDetails.add(OrderDetail.builder()
                    .orderId(orderCreatedEvent.getOrderId())
                    .productId(product.getId())
                    .priceAtTime(item.getPriceAtTime())
                    .quantity(item.getQuantity())
                    .build());
        }

        // 3️⃣ Lưu batch 1 lần duy nhất
        productRepository.saveAll(products);
        orderDetailRepository.saveAll(orderDetails);

        return true;
    }
    @Transactional
    public boolean returnStockByOrderId(String orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if (orderDetails.isEmpty()) return false;

        // Lấy tất cả productId
        List<String> productIds = orderDetails.stream()
                .map(OrderDetail::getProductId)
                .toList();

        List<Product> products = productRepository.findAllById(productIds);
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        for (OrderDetail detail : orderDetails) {
            Product product = productMap.get(detail.getProductId());
            if (product == null) continue;

            long reserved = product.getReservedStock() - detail.getQuantity();
            product.setReservedStock(Math.max(reserved, 0));
        }

        productRepository.saveAll(products);
        return true;
    }

    public List<OrderItemProduct> getStocks(DataOrderCreated orderCreatedEvent) {
        List<String> productIds = orderCreatedEvent.getListItemDetail()
                .stream()
                .map(ItemDetail::getProductId)
                .toList();

        Map<String, Product> productMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        return orderCreatedEvent.getListItemDetail().stream()
                .map(item -> {
                    Product product = productMap.get(item.getProductId());
                    return OrderItemProduct.builder()
                            .productId(product.getId())
                            .productName(product.getName())
                            .imageUrl(product.getImageUrl())
                            .priceAtTime(item.getPriceAtTime())
                            .quantity(item.getQuantity())
                            .build();
                })
                .toList();
    }


    public List<OrderItemProduct> getDataOrderProduct(String orderId) {
        // Lấy danh sách orderDetail theo orderId
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        List<OrderItemProduct> orderItemProducts = new ArrayList<>();

        if (orderDetails.isEmpty()) {
            // log.warn("Không tìm thấy OrderDetail cho orderId={}", orderId);
            return null;
        }

        for (OrderDetail orderDetail : orderDetails) {
            Product product =
                    productRepository.findById(orderDetail.getProductId()).orElse(null);

            if (product == null) {
                //  log.warn("Không tìm thấy Product với id={}", orderDetail.getProductId());
                return null;
            }
            OrderItemProduct orderItemProduct = OrderItemProduct.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .imageUrl(product.getImageUrl())
                    .priceAtTime(orderDetail.getPriceAtTime())
                    .quantity(orderDetail.getQuantity())
                    .build();
            orderItemProducts.add(orderItemProduct);
        }

        return orderItemProducts;
    }
}
