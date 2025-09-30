package com.samsung.payment_service.service;

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
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentService {

    PaymentMapper paymentMapper;
    PaymentRepository paymentRepository;
    KafkaTemplate<String, String> kafkaTemplate;

    public PaymentResponse createPayment(PaymentRequest paymentRequest){

        if("SUCCESS".equals(paymentRequest.getStatus())){
            Payment payment = paymentMapper.toPayment(paymentRequest);

            kafkaTemplate.send("PaymentSuccess",paymentRequest.getOrderId(),paymentRequest.getOrderId());
            return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
        }
        else{
            kafkaTemplate.send("PaymentFailed",paymentRequest.getOrderId(),paymentRequest.getOrderId());
            throw new AppException(ErrorCode.PAYMENT_FAILS);
        }
    }

    public List<PaymentResponse> getListPayment(){
        return paymentMapper.toPaymentResponses(paymentRepository.findAll());
    }

    public Payment getPaymentByOrderId(String orderId){
        return paymentRepository.findByOrderId(orderId).orElseThrow(()->{
            throw  new RuntimeException();
        });
    }
}
