package com.samsung.order_service.controller;

import com.samsung.event.dto.OrderStockStatus;
import com.samsung.order_service.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ListeningController {

    @Autowired
    OrderService orderService;

    KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "check-order-stock")
    public void listeningOrderCreated(OrderStockStatus orderStockStatus){
        if(!orderStockStatus.getStatus()){
            // het hang
            orderService.CancelOrder(orderStockStatus.getOrderId());

        }
        kafkaTemplate.send("current-status-order", orderStockStatus);
    }

}
