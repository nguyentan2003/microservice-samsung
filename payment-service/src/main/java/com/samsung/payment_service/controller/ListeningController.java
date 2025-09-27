package com.samsung.payment_service.controller;

import com.samsung.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ListeningController {

    private final PaymentService paymentService;

    @KafkaListener(topics = "current-status-order")
    public void CurrentStatusOrder(OrderStockStatus orderStockStatus){
        if(orderStockStatus.getStatus()){
            log.info("data: don hang da duoc giu thanh cong");
        }else log.info("data: don hang da bi huy");;

    }



}

