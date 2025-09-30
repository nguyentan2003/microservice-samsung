package com.samsung.order_service.controller;

import com.samsung.order_service.entity.Order;
import com.samsung.order_service.exception.OrderStatus;
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
            if(topic.equals("OrderSuccess3") || topic.equals("OrderCanceled3")) {
                Order order = orderService.getOrderById(orderId);
                kafkaObject.send(topic, orderId, orderMapper.toDataOrder(order));
            }
            else {
                kafkaTemplate.send(topic, orderId, orderId);
            }
        }
    }


    @KafkaListener(topics = "OrderStockReserved")
    public void listeningStockSuccess(String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) return;

        if (PaymentType.PREPAID.equals(order.getPaymentType())) {
            switch (order.getStatus()) {
                case OrderStatus.PENDING -> updateAndSend(orderId, OrderStatus.STOCK_RESERVED, null);
                case OrderStatus.PAYMENT_SUCCESS ->updateAndSend(orderId, OrderStatus.SUCCESS, "OrderSuccess3");
                case OrderStatus.PAYMENT_FAILED -> updateAndSend(orderId, OrderStatus.CANCELED, "ReturnStock");
            }
        } else {
            updateAndSend(orderId, OrderStatus.SUCCESS, "OrderSuccess3");
        }
    }

    @KafkaListener(topics = "OrderStockFailed")
    public void listeningStockFailed(String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) return;

        if (PaymentType.PREPAID.equals(order.getPaymentType())) {
            switch (order.getStatus()) {
                case OrderStatus.PENDING -> updateAndSend(orderId, OrderStatus.STOCK_FAILED, null);
                case OrderStatus.PAYMENT_SUCCESS -> updateAndSend(orderId, OrderStatus.CANCELED, "RefundMoney");
                case OrderStatus.PAYMENT_FAILED -> updateAndSend(orderId, OrderStatus.CANCELED, "OrderCanceled3");
            }
        } else {
            updateAndSend(orderId, OrderStatus.CANCELED, "OrderCanceled3");
        }
    }

    @KafkaListener(topics = "PaymentFailed")
    public void listeningPaymentFailed(String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) return;

        switch (order.getStatus()) {
            case OrderStatus.PENDING -> updateAndSend(orderId, OrderStatus.PAYMENT_FAILED, null);
            case OrderStatus.STOCK_RESERVED -> updateAndSend(orderId, OrderStatus.CANCELED, "ReturnStock");
            case OrderStatus.STOCK_FAILED -> updateAndSend(orderId, OrderStatus.CANCELED, "OrderCanceled3");
        }
    }

    @KafkaListener(topics = "PaymentSuccess")
    public void listeningPaymentSuccess(String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) return;

        switch (order.getStatus()) {
            case OrderStatus.PENDING -> updateAndSend(orderId, OrderStatus.PAYMENT_SUCCESS, null);
            case OrderStatus.STOCK_RESERVED -> updateAndSend(orderId, OrderStatus.SUCCESS, "OrderSuccess3");
            case OrderStatus.STOCK_FAILED -> updateAndSend(orderId, OrderStatus.CANCELED, "RefundMoney");
            case OrderStatus.CANCELED -> updateAndSend(orderId, OrderStatus.CANCELED, "RefundMoney");
        }
    }
}
