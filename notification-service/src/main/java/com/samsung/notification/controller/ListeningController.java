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

    @KafkaListener(topics = "OrderSuccess")
    public void OrderSuccess(DataOrder dataOrder){

        log.info("data - orderSuccess : {}",dataOrder);

    }

    @KafkaListener(topics = "OrderCanceled")
    public void OrderCanceled(DataOrder dataOrder){

        log.info("data - OrderCanceled : {}",dataOrder);

    }

}

