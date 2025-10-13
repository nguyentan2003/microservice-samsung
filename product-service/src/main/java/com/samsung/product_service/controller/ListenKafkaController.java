package com.samsung.product_service.controller;

import com.samsung.data_static.Topic;
import com.samsung.event.dto.DataOrderCreated;
import com.samsung.event.dto.DataOrderProduct;
import com.samsung.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ListenKafkaController {
    private final ProductService productService;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplateObject;

    @KafkaListener(topics = Topic.ORDER_CREATED)
    public void listeningOrderCreated(DataOrderCreated event) {
        if (productService.checkStock(event)) {
            kafkaTemplate.send(Topic.ORDER_STOCK_RESERVED, event.getOrderId(), event.getOrderId());
        } else {
            kafkaTemplate.send(Topic.ORDER_STOCK_FAILED, event.getOrderId(), event.getOrderId());
            log.info("het hang mot so san pham");
        }

        DataOrderProduct dataOrderProduct = DataOrderProduct.builder()
                .orderId(event.getOrderId())
                .orderItemProducts(productService.getStocks(event))
                .build();
        kafkaTemplateObject.send(Topic.ORDER_PRODUCT_DATA, event.getOrderId(), dataOrderProduct);
        log.info("gửi danh sáchh sản phẩm");
    }

    @KafkaListener(topics = Topic.RETURN_STOCK)
    public void listeningReturnStock(String orderId) {
        if (productService.returnStockByOrderId(orderId)) {
            log.info("return stock {} thanh cong", orderId);
        } else {
            log.info("return stock {} fail", orderId);
        }
    }
}
