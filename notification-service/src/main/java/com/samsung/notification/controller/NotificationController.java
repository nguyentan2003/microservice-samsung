package com.samsung.notification.controller;



import com.samsung.notification.entity.Notification;
import com.samsung.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;


    @GetMapping("/stream/{userId}")
    public SseEmitter stream(@PathVariable String userId) {
        return notificationService.subscribe(userId);
    }

    @PostMapping("/send")
    public String send(@RequestBody Notification notification) {
        notification.setSentAt(LocalDateTime.now());
        notificationService.sendNotification(notification);
        return "thành công";
    }
    @GetMapping("/getAll")
    public Object getAll() {

        return notificationService.getAll();
    }

    @GetMapping("/get-notification-of-user/{userId}")
    public List<Notification> getListOfUser(@PathVariable String userId) {

        return notificationService.getListOfUser(userId);
    }
}

