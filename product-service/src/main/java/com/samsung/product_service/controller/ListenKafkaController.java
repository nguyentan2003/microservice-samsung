package com.samsung.product_service.controller;

import com.samsung.event.dto.OrderCreatedEvent;
import com.samsung.event.dto.OrderStockStatus;
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

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order-created3")
    public void listeningOrderCreated(OrderCreatedEvent event){
        if(productService.checkStock(event)){

            OrderStockStatus orderStockStatus = OrderStockStatus.builder()
                    .orderId(event.getOrderId())
                    .status(true)
                    .userId(event.getUserId())
                    .build();
            kafkaTemplate.send("check-order-stock", orderStockStatus);
            log.info("giu hang thanh cong");

        }else {
            OrderStockStatus orderStockStatus = OrderStockStatus.builder()
                    .orderId(event.getOrderId())
                    .status(false)
                    .userId(event.getUserId())
                    .build();
            kafkaTemplate.send("check-order-stock", orderStockStatus);

            log.info("het hang mot so san pham");
        }
    }
}
