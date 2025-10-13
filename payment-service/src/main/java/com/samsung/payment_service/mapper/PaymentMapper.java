package com.samsung.payment_service.mapper;

import com.samsung.event.dto.DataPayment;
import com.samsung.payment_service.dto.request.PaymentRequest;
import com.samsung.payment_service.dto.response.PaymentResponse;
import com.samsung.payment_service.entity.Payment;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponse toPaymentResponse(Payment payment);

    Payment toPayment(PaymentRequest request);

    List<PaymentResponse> toPaymentResponses(List<Payment> payments);

    DataPayment toDataPayment(Payment payment);
}
