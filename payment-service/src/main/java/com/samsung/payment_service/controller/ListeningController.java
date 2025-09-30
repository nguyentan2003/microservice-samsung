package com.samsung.payment_service.controller;

import com.samsung.event.dto.DataPayment;
import com.samsung.event.dto.DataPushOrderSuccess;
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

    @KafkaListener(topics = "PushDataOrderSuccess3")
    public void PushDataOrderSuccess(DataPushOrderSuccess dataPushOrderSuccess){

        if(dataPushOrderSuccess.getPaymentType().equals("PREPAID")){
            Payment payment = paymentService.getPaymentByOrderId(dataPushOrderSuccess.getOrderId());
            DataPayment dataPayment = paymentMapper.toDataPayment(payment);
            kafkaObject.send("OrderSuccessPayment3",dataPushOrderSuccess.getOrderId(),dataPayment);
            log.info("gui data payment for customer summary success {}",dataPayment);
        }else{
            log.info("{} order nay POSTPAID nen khong gui data",dataPushOrderSuccess.getOrderId());
        }


    }

}

