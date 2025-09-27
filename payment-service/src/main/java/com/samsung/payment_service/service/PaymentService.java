package com.samsung.payment_service.service;


import com.samsung.event.dto.PaymentStatus;
import com.samsung.payment_service.dto.request.PaymentRequest;
import com.samsung.payment_service.dto.response.PaymentResponse;
import com.samsung.payment_service.entity.Payment;
import com.samsung.payment_service.exception.AppException;
import com.samsung.payment_service.exception.ErrorCode;
import com.samsung.payment_service.mapper.PaymentMapper;
import com.samsung.payment_service.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentService {

    PaymentMapper paymentMapper;
    PaymentRepository paymentRepository;
    KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentResponse createPayment(PaymentRequest paymentRequest){
        PaymentStatus paymentStatus = PaymentStatus.builder()
                .orderId(paymentRequest.getOrderId())
                .build();
        if("SUCCESS".equals(paymentRequest.getStatus())){
            Payment payment = paymentMapper.toPayment(paymentRequest);

            paymentStatus.setStatus(true);
            kafkaTemplate.send("payment-successful", paymentStatus);
            return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
        }
        else{
            paymentStatus.setStatus(false);
            kafkaTemplate.send("payment-fails", paymentStatus);
            throw new AppException(ErrorCode.PAYMENT_FAILS);
        }
    }

    public List<PaymentResponse> getListPayment(){
        return paymentMapper.toPaymentResponses(paymentRepository.findAll());
    }
}
