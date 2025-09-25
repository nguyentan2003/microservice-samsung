package com.samsung.notification.controller;


import com.samsung.event.dto.DataEvent;
import com.samsung.event.dto.OrderStockStatus;
import com.samsung.notification.entity.Notification;
import com.samsung.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ListeningController {
    private final NotificationService notificationService;

    // topic va group_id , và message đc map từ giá trị data truyền bên producer;
    @KafkaListener(topics = "register_user4")
    public void register(DataEvent dataEvent){
        log.info("data: {}",dataEvent);
    }

    @KafkaListener(topics = "current-status-order")
    public void CurrentStatusOrder(OrderStockStatus orderStockStatus){
        if(orderStockStatus.getStatus()){
            log.info("data: don hang da duoc giu thanh cong");
        }else log.info("data: don hang da bi huy");;

    }

}

