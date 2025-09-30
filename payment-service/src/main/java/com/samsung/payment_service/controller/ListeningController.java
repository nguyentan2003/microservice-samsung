package com.samsung.payment_service.controller;

import com.samsung.event.dto.DataPayment;
import com.samsung.event.dto.DataPaymentType;
import com.samsung.payment_service.entity.Payment;
import com.samsung.payment_service.mapper.PaymentMapper;
import com.samsung.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ListeningController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;


    @KafkaListener(topics = "RefundMoney")
    public void RefundMoney(String orderId){
        log.info("hoàn tiền thành công cho order : {}",orderId);

    }

    private final KafkaTemplate<String, Object> kafkaObject;

    @KafkaListener(topics = "PushDataOrderSuccess2")
    public void PushDataOrderSuccess(DataPaymentType dataPaymentType){

        if(dataPaymentType.getPaymentType().equals("PREPAID")){
            Payment payment = paymentService.getPaymentByOrderId(dataPaymentType.getOrderId());
            DataPayment dataPayment = paymentMapper.toDataPayment(payment);
            kafkaObject.send("OrderSuccessPayment3",dataPaymentType.getOrderId(),dataPayment);
            log.info("gui data payment for customer summary success {}",dataPayment);
        }else{
            log.info("{} order nay POSTPAID nen khong gui data",dataPaymentType.getOrderId());
        }


    }

}

