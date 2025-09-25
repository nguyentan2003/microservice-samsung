package com.samsung.notification.service;


import com.samsung.notification.configuration.CustomJwtDecoder;
import com.samsung.notification.entity.Notification;
import com.samsung.notification.repository.NotificationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;




@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    NotificationRepository repository;
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    // Đăng ký kết nối SSE cho user
    // Đăng ký kết nối SSE cho user
    public SseEmitter subscribe(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new SecurityException("❌ Chưa đăng nhập");
        }

        // principal trong JWT Authentication chứa claims
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String sub = jwt.getClaim("sub"); // chính là userId trong token

        if (!userId.equals(sub)) {
            throw new SecurityException("❌ Không có quyền subscribe userId khác");
        }

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));

        return emitter;
    }


    // Gửi thông báo cho user
    public void sendNotification(Notification notification) {
         // lưu DB

        repository.save(notification);
        SseEmitter emitter = emitters.get(notification.getUserId());


        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(notification));
            } catch (Exception e) {
                emitters.remove(notification.getUserId());
            }
        }
    }

    public Object getAll() {
        // lưu DB

      return repository.findAll();
    }
}

