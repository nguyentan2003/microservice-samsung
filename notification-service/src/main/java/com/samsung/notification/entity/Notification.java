package com.samsung.notification.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Document(collection = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification {
    @Id
    private String notificationId;

    private String userId;
    private String type;
    private String message;
    private LocalDateTime sentAt;

    //    @JsonProperty("isRead")
    private boolean isRead;
}
