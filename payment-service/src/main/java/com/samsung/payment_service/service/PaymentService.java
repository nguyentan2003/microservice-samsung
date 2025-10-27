package com.samsung.payment_service.service;

import com.samsung.data_static.OrderStatus;
import com.samsung.data_static.Topic;
import com.samsung.event.dto.DataPayment;
import com.samsung.event.dto.NotificationStatus;
import com.samsung.payment_service.dto.request.PaymentRequest;
import com.samsung.payment_service.dto.response.PaymentResponse;
import com.samsung.payment_service.entity.Payment;
import com.samsung.payment_service.exception.AppException;
import com.samsung.payment_service.exception.ErrorCode;
import com.samsung.payment_service.mapper.PaymentMapper;
import com.samsung.payment_service.repository.PaymentRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentService {

    PaymentMapper paymentMapper;
    PaymentRepository paymentRepository;
    KafkaTemplate<String, String> kafkaTemplate;
    KafkaTemplate<String, Object> objectKafkaTemplate;

    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        NotificationStatus notificationStatus = NotificationStatus.builder()
                .userId(paymentRequest.getUserId())
                .orderId(paymentRequest.getOrderId())
                .message(Topic.PAYMENT_CONFIRMED)
                .build();

        Payment payment = paymentMapper.toPayment(paymentRequest);
        if (OrderStatus.SUCCESS.equals(paymentRequest.getStatus())) {
            objectKafkaTemplate.send(Topic.NOTIFICATION_STATUS, paymentRequest.getOrderId(), notificationStatus);

            DataPayment dataPayment = paymentMapper.toDataPayment(payment);
            kafkaTemplate.send(Topic.PAYMENT_SUCCESS, paymentRequest.getOrderId(), paymentRequest.getOrderId());
            objectKafkaTemplate.send(Topic.DATA_PAYMENT_SUCCESS, paymentRequest.getOrderId(), dataPayment);

            return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
        } else {
            kafkaTemplate.send(Topic.PAYMENT_FAILED, paymentRequest.getOrderId(), paymentRequest.getOrderId());
            return paymentMapper.toPaymentResponse(payment);
        }
    }

    public List<PaymentResponse> getListPayment() {
        return paymentMapper.toPaymentResponses(paymentRepository.findAll());
    }

    public Boolean refundMoney(String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> {
            throw new AppException(ErrorCode.ORDER_NOT_EXISTED);
        });
        payment.setStatus("REFUND_MONEY_SUCCESS");
        paymentRepository.save(payment);

        return true;
    }

    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId).orElseThrow(() -> {
            throw new AppException(ErrorCode.ORDER_NOT_EXISTED);
        });
    }
}
