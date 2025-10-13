package com.samsung.notification.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.samsung.notification.dto.ApiResponse;
import com.samsung.notification.entity.Notification;
import com.samsung.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/stream/{userId}")
    public SseEmitter stream(@PathVariable String userId) {
        return notificationService.subscribe(userId);
    }

    @PatchMapping("/mark-read/{userId}")
    public ApiResponse<Boolean> markAsRead(@PathVariable String userId) {
        notificationService.markAllAsRead(userId);
        return ApiResponse.<Boolean>builder()
                .message("Mark read success")
                .result(true)
                .code(1000)
                .build();
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
    public ApiResponse<List<Notification>> getListOfUser(@PathVariable String userId) {

        return ApiResponse.<List<Notification>>builder()
                .code(1000)
                .result(notificationService.getListOfUser(userId))
                .message("Success")
                .build();
    }

    @DeleteMapping("/delete-all")
    public ApiResponse<Boolean> deleteAll() {
        notificationService.deleteAll();
        return ApiResponse.<Boolean>builder()
                .code(1000)
                .result(true)
                .message("Success")
                .build();
    }
}
