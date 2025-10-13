package com.samsung.customer_summary.controller;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import com.samsung.customer_summary.entity.CustomerSummary;
import com.samsung.customer_summary.repository.CustomerSummaryRepository;
import com.samsung.customer_summary.service.CustomerSummaryService;
import com.samsung.customer_summary.service.PendingMessageService;
import com.samsung.data_static.OrderStatus;
import com.samsung.data_static.Topic;
import com.samsung.event.dto.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ListeningController {
    private final CustomerSummaryService customerSummaryService;
    private final CustomerSummaryRepository customerSummaryRepository;
    private final MongoTemplate mongoTemplate;
    private final PendingMessageService pendingMessageService;
    private final KafkaTemplate<String, Object> objectKafkaTemplate;

    @KafkaListener(topics = Topic.ORDER_CREATED)
    public void listeningOrderCreated(DataOrderCreated dataOrder) {
        CustomerSummary customerSummary = CustomerSummary.builder()
                .userId(dataOrder.getUserId())
                .orderId(dataOrder.getOrderId())
                .shippingAddress(dataOrder.getAddress())
                .orderDate(dataOrder.getOrderDate())
                .orderStatus(dataOrder.getStatus())
                .totalAmount(dataOrder.getTotalAmount())
                .paymentType(dataOrder.getPaymentType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        customerSummaryRepository.save(customerSummary); // Insert mới
        objectKafkaTemplate.send(Topic.CUSTOMER_SUMMARY_ORDER_CREATED, dataOrder.getOrderId(), dataOrder);
        log.info("Tạo customer summary thành công cho orderId={}", dataOrder.getOrderId());
    }

    @KafkaListener(topics = Topic.ORDER_PRODUCT_DATA)
    public void ListeningOrderProductData(DataOrderProduct dataOrderProduct) {

        Query query = new Query(Criteria.where("orderId").is(dataOrderProduct.getOrderId()));

        CustomerSummary summary = mongoTemplate.findOne(query, CustomerSummary.class);

        if (summary == null) {
            pendingMessageService.savePending(
                    Topic.ORDER_PRODUCT_DATA, dataOrderProduct, dataOrderProduct.getOrderId());
            return;
        }
        Update update = new Update().set("orderItemSummaries", dataOrderProduct.getOrderItemProducts());
        mongoTemplate.updateFirst(query, update, CustomerSummary.class);

        log.info("Update data product orderId={}", dataOrderProduct.getOrderId());
    }

    @KafkaListener(topics = Topic.PUSH_DATA_INFO_ORDER_CREATED)
    public void GetDataFromProfile(DataUserInfo dataUserInfo) {

        Query query = new Query(Criteria.where("orderId").is(dataUserInfo.getOrderId()));

        CustomerSummary summary = mongoTemplate.findOne(query, CustomerSummary.class);

        if (summary == null) {
            pendingMessageService.savePending(
                    Topic.PUSH_DATA_INFO_ORDER_CREATED, dataUserInfo, dataUserInfo.getOrderId());
            return;
        }

        Update update = new Update()
                .set("username", dataUserInfo.getUsername())
                .set("fullName", dataUserInfo.getFullName())
                .set("email", dataUserInfo.getEmail())
                .set("phone", dataUserInfo.getPhone())
                .set("address", dataUserInfo.getAddress());

        mongoTemplate.updateFirst(query, update, CustomerSummary.class);

        log.info("Update user info cho orderId={}", dataUserInfo.getOrderId());
    }

    @KafkaListener(topics = Topic.DATA_PAYMENT_SUCCESS)
    public void listeningPaymentSuccess(DataPayment dataPayment) {

        Query query = new Query(Criteria.where("orderId").is(dataPayment.getOrderId()));

        CustomerSummary summary = mongoTemplate.findOne(query, CustomerSummary.class);

        if (summary == null) {
            pendingMessageService.savePending(Topic.DATA_PAYMENT_SUCCESS, dataPayment, dataPayment.getOrderId());
            return;
        }

        Update update = new Update()
                .set("paymentMethod", dataPayment.getPaymentMethod())
                .set("totalAmount", dataPayment.getAmount())
                .set("paymentStatus", OrderStatus.PAYMENT_SUCCESS)
                .set("transactionId", dataPayment.getTransactionId())
                .set("paymentTime", dataPayment.getPaymentTime());

        mongoTemplate.updateFirst(query, update, CustomerSummary.class);

        log.info("Update payment thành công cho orderId={}", dataPayment.getOrderId());
    }

    @KafkaListener(topics = Topic.REFUND_MONEY)
    public void listeningRefundMoney(String orderId) {

        Query query = new Query(Criteria.where("orderId").is(orderId));

        CustomerSummary summary = mongoTemplate.findOne(query, CustomerSummary.class);

        if (summary == null) {
            pendingMessageService.savePending(Topic.REFUND_MONEY, orderId, orderId);
            return;
        }

        Update update = new Update()
                .set("paymentStatus", OrderStatus.REFUND_MONEY)
                .set("orderStatus", OrderStatus.CANCELED)
                .set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, CustomerSummary.class);

        log.info("Update refund money cho orderId={}", orderId);
    }

    @KafkaListener(topics = Topic.PAYMENT_FAILED)
    public void listeningPaymentFailed(String orderId) {

        Query query = new Query(Criteria.where("orderId").is(orderId));
        CustomerSummary summary = mongoTemplate.findOne(query, CustomerSummary.class);

        if (summary == null) {
            pendingMessageService.savePending(Topic.PAYMENT_FAILED, orderId, orderId);
            return;
        }
        Update update = new Update()
                .set("paymentStatus", OrderStatus.PAYMENT_FAILED)
                .set("orderStatus", OrderStatus.CANCELED)
                .set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, CustomerSummary.class);

        log.info("Update payment failed cho orderId={}", orderId);
    }

    @KafkaListener(topics = Topic.ORDER_STOCK_RESERVED)
    public void listeningOrderStockReserved(String orderId) {

        Query query = new Query(Criteria.where("orderId").is(orderId));
        CustomerSummary summary = mongoTemplate.findOne(query, CustomerSummary.class);

        if (summary == null) {
            pendingMessageService.savePending(Topic.ORDER_STOCK_RESERVED, orderId, orderId);
            return;
        }
        Update update =
                new Update().set("statusStock", OrderStatus.STOCK_RESERVED).set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, CustomerSummary.class);

        log.info("Update stock reserved cho orderId={}", orderId);
    }

    @KafkaListener(topics = Topic.RETURN_STOCK)
    public void listeningReturnStock(String orderId) {

        Query query = new Query(Criteria.where("orderId").is(orderId));
        CustomerSummary summary = mongoTemplate.findOne(query, CustomerSummary.class);

        if (summary == null) {
            pendingMessageService.savePending(Topic.RETURN_STOCK, orderId, orderId);
            return;
        }
        Update update = new Update()
                .set("statusStock", OrderStatus.RETURN_STOCK)
                .set("orderStatus", OrderStatus.CANCELED)
                .set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, CustomerSummary.class);

        log.info("Update return stock cho orderId={}", orderId);
    }

    @KafkaListener(topics = Topic.ORDER_STOCK_FAILED)
    public void listeningOrderStockFailed(String orderId) {

        Query query = new Query(Criteria.where("orderId").is(orderId));
        CustomerSummary summary = mongoTemplate.findOne(query, CustomerSummary.class);

        if (summary == null) {
            pendingMessageService.savePending(Topic.ORDER_STOCK_FAILED, orderId, orderId);
            return;
        }
        Update update = new Update()
                .set("statusStock", OrderStatus.STOCK_FAILED)
                .set("orderStatus", OrderStatus.CANCELED)
                .set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, CustomerSummary.class);

        log.info("Update stock failed cho orderId={}", orderId);
    }

    @KafkaListener(topics = Topic.ORDER_SUCCESS)
    public void listeningOrderSuccess3(DataOrder dataOrder) {

        Query query = new Query(Criteria.where("orderId").is(dataOrder.getId()));
        CustomerSummary summary = mongoTemplate.findOne(query, CustomerSummary.class);

        if (summary == null) {
            pendingMessageService.savePending(Topic.ORDER_SUCCESS, dataOrder, dataOrder.getId());
            return;
        }
        Update update = new Update().set("orderStatus", OrderStatus.SUCCESS).set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, CustomerSummary.class);

        log.info("Update order -> success cho orderId={}", dataOrder.getId());
    }

    @KafkaListener(topics = Topic.ORDER_CANCELED)
    public void listeningOrderCanceled3(DataOrder dataOrder) {

        Query query = new Query(Criteria.where("orderId").is(dataOrder.getId()));
        CustomerSummary summary = mongoTemplate.findOne(query, CustomerSummary.class);

        if (summary == null) {
            pendingMessageService.savePending(Topic.REFUND_MONEY, dataOrder, dataOrder.getId());
            return;
        }
        Update update = new Update().set("orderStatus", OrderStatus.CANCELED).set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, CustomerSummary.class);

        log.info("Update order -> canceled cho orderId={}", dataOrder.getId());
    }

    @KafkaListener(topics = Topic.UPDATE_ORDER_STATUS)
    public void listeningOrderChangeStatusShipping(UpdateOrderStatus updateOrderStatus) {
        Query query = new Query(Criteria.where("orderId").is(updateOrderStatus.getOrderId()));
        CustomerSummary summary = mongoTemplate.findOne(query, CustomerSummary.class);
        Update update =
                new Update().set("orderStatus", updateOrderStatus.getStatus()).set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, CustomerSummary.class);

        log.info("Update order -> {} cho orderId={}", updateOrderStatus.getStatus(), updateOrderStatus.getOrderId());
    }
}
