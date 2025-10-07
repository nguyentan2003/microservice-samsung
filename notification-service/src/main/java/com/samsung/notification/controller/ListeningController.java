package com.samsung.notification.controller;

import com.samsung.data_static.OrderStatus;
import com.samsung.data_static.Topic;
import com.samsung.event.dto.*;

import com.samsung.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ListeningController {
    private final NotificationService notificationService;


    @KafkaListener(topics = Topic.ORDER_CREATED)
    public void listeningOrderCreated(DataOrderCreated dataOrder){

        log.info("Tạo customer summary thành công cho orderId={}", dataOrder.getOrderId());
    }

    @KafkaListener(topics = Topic.ORDER_PRODUCT_DATA)
    public void ListeningOrderProductData(DataOrderProduct dataOrderProduct){


        log.info("Update data product orderId={}", dataOrderProduct.getOrderId());
    }

    @KafkaListener(topics = Topic.PUSH_DATA_INFO_ORDER_CREATED)
    public void GetDataFromProfile(DataUserInfo dataUserInfo){
        log.info("Update user info cho orderId={}", dataUserInfo.getOrderId());
    }

    @KafkaListener(topics = Topic.DATA_PAYMENT_SUCCESS)
    public void listeningPaymentSuccess(DataPayment dataPayment){

        log.info("Update payment thành công cho orderId={}", dataPayment.getOrderId());
    }

    @KafkaListener(topics = Topic.REFUND_MONEY)
    public void listeningRefundMoney(String orderId){

        log.info("Update refund money cho orderId={}", orderId);
    }

    @KafkaListener(topics = Topic.PAYMENT_FAILED)
    public void listeningPaymentFailed(String orderId){

        log.info("Update payment failed cho orderId={}", orderId);
    }

    @KafkaListener(topics = Topic.ORDER_STOCK_RESERVED)
    public void listeningOrderStockReserved(String orderId){
        log.info("Update stock reserved cho orderId={}", orderId);
    }

    @KafkaListener(topics = Topic.RETURN_STOCK)
    public void listeningReturnStock(String orderId){
        log.info("Update return stock cho orderId={}", orderId);
    }

    @KafkaListener(topics = Topic.ORDER_STOCK_FAILED)
    public void listeningOrderStockFailed(String orderId){


        log.info("Update stock failed cho orderId={}", orderId);
    }

    @KafkaListener(topics = Topic.ORDER_SUCCESS)
    public void listeningOrderSuccess3(DataOrder dataOrder){

        log.info("Update order -> success cho orderId={}", dataOrder.getId());
    }

    @KafkaListener(topics = Topic.ORDER_CANCELED)
    public void listeningOrderCanceled3(DataOrder dataOrder){


        log.info("Update order -> canceled cho orderId={}", dataOrder.getId());
    }

}

