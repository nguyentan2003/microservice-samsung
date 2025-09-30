package com.samsung.customer_summary.controller;

import com.samsung.customer_summary.entity.CustomerSummary;
import com.samsung.customer_summary.mapper.CustomerSummaryMapper;
import com.samsung.customer_summary.repository.CustomerSummaryRepository;
import com.samsung.customer_summary.service.CustomerSummaryService;
import com.samsung.event.dto.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ListeningController {
    private final CustomerSummaryService customerSummaryService;
    private final CustomerSummaryRepository customerSummaryRepository;
    private final CustomerSummaryMapper customerSummaryMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Object> objectKafkaTemplate;

    @KafkaListener(topics = "OrderSuccess3")
    public void GetDataFromOrderService(DataOrder dataOrder){
        log.info("helo check");
        CustomerSummary customerSummary = CustomerSummary.builder()
                .userId(dataOrder.getUserId())
                .orderId(dataOrder.getId())
                .shippingAddress(dataOrder.getAddress())
                .orderDate(dataOrder.getOrderDate())
                .orderStatus(dataOrder.getStatus())
                .totalAmount(dataOrder.getTotalAmount())
                .paymentType(dataOrder.getPaymentType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        customerSummaryRepository.save(customerSummary);
        DataPushOrderSuccess dataPushOrderSuccess = DataPushOrderSuccess.builder()
                .orderId(dataOrder.getId())
                .userId(dataOrder.getUserId())
                .paymentType(dataOrder.getPaymentType())
                .build();

        objectKafkaTemplate.send("PushDataOrderSuccess3", dataOrder.getId(),dataPushOrderSuccess );

        //objectKafkaTemplate.send("PushDataOrderUserInfoSuccess", dataOrder.getId(), dataSendIdentity);
        log.info("tao customer summary success {}",dataOrder.getId());
    }

    @KafkaListener(topics = "OrderSuccessUserInfo3")
    public void GetDataFromProfile(DataUserInfo dataUserInfo){
        CustomerSummary customerSummary = customerSummaryService.getSummaryByOrderId(dataUserInfo.getOrderId());
        customerSummary.setUsername(dataUserInfo.getUsername());
        customerSummary.setFullName(dataUserInfo.getFullName());
        customerSummary.setEmail(dataUserInfo.getEmail());
        customerSummary.setPhone(dataUserInfo.getPhone());
        customerSummary.setAddress(dataUserInfo.getAddress());

        customerSummaryRepository.save(customerSummary);
        log.info("update userinfo for customer summary success {}",dataUserInfo.getOrderId());

    }

    @KafkaListener(topics = "OrderSuccessPayment3")
    public void GetDataFromPayment(DataPayment dataPayment){
        CustomerSummary customerSummary = customerSummaryService.getSummaryByOrderId(dataPayment.getOrderId());

        customerSummary.setPaymentMethod(dataPayment.getPaymentMethod());
        customerSummary.setTotalAmount(dataPayment.getAmount());
        customerSummary.setPaymentStatus(dataPayment.getStatus());
        customerSummary.setTransactionId(dataPayment.getTransactionId());
        customerSummary.setPaymentTime(dataPayment.getPaymentTime());

        customerSummaryRepository.save(customerSummary);
        log.info("update payment for customer summary success {}",dataPayment.getOrderId());

    }

    @KafkaListener(topics = "OrderSuccessProduct3")
    public void GetDataFromProduct(DataOrderProduct dataOrderProduct){
        CustomerSummary customerSummary = customerSummaryService.getSummaryByOrderId(dataOrderProduct.getOrderId());

       customerSummary.setOrderItemSummaries(customerSummaryMapper.toOrderItemSummaries(dataOrderProduct.getOrderItemProducts()));

        customerSummaryRepository.save(customerSummary);
        log.info("update list product for customer summary success {}",dataOrderProduct.getOrderId());
    }


}

