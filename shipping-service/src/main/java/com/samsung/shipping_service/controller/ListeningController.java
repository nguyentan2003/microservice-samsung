package com.samsung.shipping_service.controller;

import com.samsung.shipping_service.service.ShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ListeningController {

    private final ShippingService shippingService;

//    @KafkaListener(topics = "current-status-order")
//    public void CurrentStatusOrder(OrderStockStatus orderStockStatus){
//        if(orderStockStatus.getStatus()){
//            log.info("data: don hang da duoc giu thanh cong");
//        }else log.info("data: don hang da bi huy");;
//
//    }



}

