package com.samsung.notification.controller;

import java.time.LocalDateTime;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import com.samsung.data_static.Topic;
import com.samsung.event.dto.*;
import com.samsung.notification.entity.Notification;
import com.samsung.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ListeningController {
    private final NotificationService notificationService;

    @KafkaListener(topics = Topic.NOTIFICATION_STATUS)
    public void listeningNotificationStatus(NotificationStatus notificationStatus) {

        Notification notification = Notification.builder()
                .userId(notificationStatus.getUserId())
                .type("INFO")
                .message("Order : " + notificationStatus.getOrderId() + " " + notificationStatus.getMessage())
                .sentAt(LocalDateTime.now())
                .isRead(false)
                .build();
        notificationService.sendNotification(notification);
        log.info("Notification Status : {}", notificationStatus);
    }

    @KafkaListener(topics = Topic.UPDATE_ORDER_STATUS)
    public void listeningOrderChangeStatus(UpdateOrderStatus orderStatus) {

        Notification notification = Notification.builder()
                .userId(orderStatus.getUserId())
                .type("INFO")
                .message("Order : " + orderStatus.getOrderId() + " " + orderStatus.getStatus())
                .sentAt(LocalDateTime.now())
                .isRead(false)
                .build();
        notificationService.sendNotification(notification);
        log.info("UPDATE_ORDER_STATUS Status : {}", orderStatus);
    }
}
