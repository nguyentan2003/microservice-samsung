package com.samsung.order_service.controller;

import com.samsung.data_static.Topic;
import com.samsung.event.dto.NotificationStatus;
import com.samsung.order_service.entity.Order;
import com.samsung.data_static.OrderStatus;
import com.samsung.order_service.exception.PaymentType;
import com.samsung.order_service.mapper.OrderMapper;
import com.samsung.order_service.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ListeningController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderMapper orderMapper;

    KafkaTemplate<String, String> kafkaTemplate;
    KafkaTemplate<String, Object> kafkaObject;

    private void updateAndSend(String orderId, String newStatus, String topic) {
        orderService.updateOrderStatus(orderId, newStatus);

        if (topic != null) {
            Order order = orderService.getOrderById(orderId);
            NotificationStatus notificationStatus = NotificationStatus.builder()
                    .userId(order.getUserId())
                    .orderId(orderId)
                    .message(topic)
                    .build();

            if(topic.equals(Topic.ORDER_SUCCESS) || topic.equals(Topic.ORDER_CANCELED)) {

                kafkaObject.send(topic, orderId, orderMapper.toDataOrder(order));
            }
            else {
                if(newStatus.equals(OrderStatus.CANCELED)){
                    kafkaObject.send(Topic.ORDER_CANCELED, orderId, orderMapper.toDataOrder(order));
                    NotificationStatus notificationStatus1 = NotificationStatus.builder()
                            .userId(order.getUserId())
                            .orderId(orderId)
                            .message(Topic.ORDER_CANCELED)
                            .build();
                    kafkaObject.send(Topic.NOTIFICATION_STATUS,orderId,notificationStatus1);
                }
                kafkaTemplate.send(topic, orderId, orderId);
            }

            kafkaObject.send(Topic.NOTIFICATION_STATUS,orderId,notificationStatus);
        }
    }

    @KafkaListener(topics = Topic.ORDER_STOCK_RESERVED)
    public void listeningStockSuccess(String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) return;

        NotificationStatus notificationStatus = NotificationStatus.builder()
                .message(Topic.ORDER_STOCK_RESERVED)
                .userId(order.getUserId())
                .orderId(orderId)
                .build();
        kafkaObject.send(Topic.NOTIFICATION_STATUS,orderId,notificationStatus);

        if (PaymentType.PREPAID.equals(order.getPaymentType())) {
            switch (order.getStatus()) {
                case OrderStatus.PENDING -> updateAndSend(orderId, OrderStatus.STOCK_RESERVED, null);
                case OrderStatus.PAYMENT_SUCCESS ->updateAndSend(orderId, OrderStatus.SUCCESS, Topic.ORDER_SUCCESS);
                case OrderStatus.PAYMENT_FAILED -> updateAndSend(orderId, OrderStatus.CANCELED, Topic.RETURN_STOCK);
                case OrderStatus.CANCELED -> updateAndSend(orderId, OrderStatus.CANCELED, Topic.RETURN_STOCK);
            }
        } else {
            updateAndSend(orderId, OrderStatus.SUCCESS, Topic.ORDER_SUCCESS);
        }
    }

    @KafkaListener(topics = Topic.ORDER_STOCK_FAILED)
    public void listeningStockFailed(String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) return;

        NotificationStatus notificationStatus = NotificationStatus.builder()
                .message(Topic.ORDER_STOCK_FAILED)
                .userId(order.getUserId())
                .orderId(orderId)
                .build();
        kafkaObject.send(Topic.NOTIFICATION_STATUS,orderId,notificationStatus);

        if (PaymentType.PREPAID.equals(order.getPaymentType())) {
            switch (order.getStatus()) {
                case OrderStatus.PENDING -> updateAndSend(orderId, OrderStatus.STOCK_FAILED, null);
                case OrderStatus.PAYMENT_SUCCESS -> updateAndSend(orderId, OrderStatus.CANCELED, Topic.REFUND_MONEY);
                case OrderStatus.PAYMENT_FAILED -> updateAndSend(orderId, OrderStatus.CANCELED, Topic.ORDER_CANCELED);
                case OrderStatus.CANCELED -> updateAndSend(orderId, OrderStatus.CANCELED, Topic.ORDER_STOCK_FAILED);
            }
        } else {
            updateAndSend(orderId, OrderStatus.CANCELED, Topic.ORDER_CANCELED);
        }
    }

    @KafkaListener(topics = Topic.PAYMENT_FAILED)
    public void listeningPaymentFailed(String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) return;
        NotificationStatus notificationStatus = NotificationStatus.builder()
                .message(Topic.PAYMENT_FAILED)
                .userId(order.getUserId())
                .orderId(orderId)
                .build();
        kafkaObject.send(Topic.NOTIFICATION_STATUS,orderId,notificationStatus);

        switch (order.getStatus()) {
            case OrderStatus.PENDING -> updateAndSend(orderId, OrderStatus.PAYMENT_FAILED, null);
            case OrderStatus.STOCK_RESERVED -> updateAndSend(orderId, OrderStatus.CANCELED, Topic.RETURN_STOCK);
            case OrderStatus.STOCK_FAILED -> updateAndSend(orderId, OrderStatus.CANCELED, Topic.ORDER_CANCELED);
            case OrderStatus.CANCELED -> updateAndSend(orderId, OrderStatus.CANCELED, Topic.PAYMENT_FAILED);
        }
    }

    @KafkaListener(topics = Topic.PAYMENT_SUCCESS)
    public void listeningPaymentSuccess(String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) return;
        NotificationStatus notificationStatus = NotificationStatus.builder()
                .message(Topic.PAYMENT_SUCCESS)
                .userId(order.getUserId())
                .orderId(orderId)
                .build();
        kafkaObject.send(Topic.NOTIFICATION_STATUS,orderId,notificationStatus);

        switch (order.getStatus()) {
            case OrderStatus.PENDING -> updateAndSend(orderId, OrderStatus.PAYMENT_SUCCESS, null);
            case OrderStatus.STOCK_RESERVED -> updateAndSend(orderId, OrderStatus.SUCCESS, Topic.ORDER_SUCCESS);
            case OrderStatus.STOCK_FAILED -> updateAndSend(orderId, OrderStatus.CANCELED, Topic.REFUND_MONEY);
            case OrderStatus.CANCELED -> updateAndSend(orderId, OrderStatus.CANCELED, Topic.REFUND_MONEY);
        }
    }
}
