package com.samsung.payment_service.service;


import com.samsung.payment_service.dto.request.PaymentRequest;
import com.samsung.payment_service.dto.response.PaymentResponse;
import com.samsung.payment_service.entity.Payment;
import com.samsung.payment_service.mapper.PaymentMapper;
import com.samsung.payment_service.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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

    public PaymentResponse createPayment(PaymentRequest paymentRequest){
        Payment payment = paymentMapper.toPayment(paymentRequest);

        return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }

    public List<PaymentResponse> getListPayment(){
        return paymentMapper.toPaymentResponses(paymentRepository.findAll());
    }
}
