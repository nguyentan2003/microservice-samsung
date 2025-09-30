package com.samsung.notification.controller;

import com.samsung.event.dto.DataOrder;

import com.samsung.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ListeningController {
    private final NotificationService notificationService;

    @KafkaListener(topics = "OrderSuccess3")
    public void OrderSuccess(DataOrder dataOrder){

        log.info("data - orderSuccess : {}",dataOrder);

    }

    @KafkaListener(topics = "OrderCanceled3")
    public void OrderCanceled(DataOrder dataOrder){

        log.info("data - OrderCanceled : {}",dataOrder);

    }

    @KafkaListener(topics = "ReturnStock")
    public void ReturnStock(String orderId){

        log.info("data - OrderCanceled - PaymentFailed : {}",orderId);

    }

    @KafkaListener(topics = "RefundMoney")
    public void RefundMoney(String orderId){

        log.info("data - OrderCanceled - StockNotAvailable : {}",orderId);

    }

}

