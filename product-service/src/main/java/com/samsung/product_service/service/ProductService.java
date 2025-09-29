package com.samsung.product_service.service;

import com.samsung.event.dto.DataOrderCreated;
import com.samsung.event.dto.ItemDetail;
import com.samsung.event.dto.OrderCreatedEvent;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final OrderDetailService orderDetailService;
    private final OrderDetailRepository orderDetailRepository;

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
                .reservedStock(0L)
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


    @Transactional
    public Boolean checkStock(DataOrderCreated orderCreatedEvent) {
        for (ItemDetail item : orderCreatedEvent.getListItemDetail()) {
            // tìm product theo id
            Product product = productRepository.findById(item.getProductId())
                    .orElse(null);

            if (product == null) {
                return false; // sản phẩm không tồn tại
            }

            // tính tồn kho khả dụng
            Long available = product.getStockQuantity() - product.getReservedStock();

            if (available < item.getQuantity()) {
                return false; // không đủ hàng
            }
        }

        // Nếu tất cả sản phẩm đều đủ → tiến hành reserve
        for (ItemDetail item : orderCreatedEvent.getListItemDetail()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow();

            product.setReservedStock(product.getReservedStock() + item.getQuantity());
            productRepository.save(product);

            // tao cac orderDetail
            OrderDetailCreationRequest orderDetailCreationRequest = OrderDetailCreationRequest.builder()
                    .orderId(orderCreatedEvent.getOrderId())
                    .productId(product.getId())
                    .priceAtTime(item.getPriceAtTime())
                    .quantity(item.getQuantity())
                    .build();
            orderDetailService.createOrderDetail(orderDetailCreationRequest);
        }

        return true;
    }

    @Transactional
    public Boolean returnStockByOrderId(String orderId) {
        // Lấy danh sách orderDetail theo orderId
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);

        if (orderDetails.isEmpty()) {
           // log.warn("Không tìm thấy OrderDetail cho orderId={}", orderId);
            return false;
        }

        for (OrderDetail orderDetail : orderDetails) {
            Product product = productRepository.findById(orderDetail.getProductId())
                    .orElse(null);

            if (product == null) {
              //  log.warn("Không tìm thấy Product với id={}", orderDetail.getProductId());
                return false;
            }

            // Kiểm tra tránh âm reservedStock
            if (product.getReservedStock() < orderDetail.getQuantity()) {
             //   log.warn("reservedStock của product {} nhỏ hơn số cần hoàn! reserved={}, cần hoàn={}",
                    //    product.getId(), product.getReservedStock(), orderDetail.getQuantity());
                return false;
            }

            // Hoàn trả số lượng
            product.setReservedStock(product.getReservedStock() - orderDetail.getQuantity());
            productRepository.save(product);
        }

        return true;
    }



}
