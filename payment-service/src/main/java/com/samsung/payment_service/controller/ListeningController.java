package com.samsung.payment_service.controller;

import com.samsung.data_static.Topic;
import com.samsung.payment_service.mapper.PaymentMapper;
import com.samsung.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ListeningController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @KafkaListener(topics = Topic.REFUND_MONEY)
    public void RefundMoney(String orderId) {

        Boolean check = paymentService.refundMoney(orderId);

        if (check) log.info("hoàn tiền thành công cho order : {}", orderId);
    }
}
