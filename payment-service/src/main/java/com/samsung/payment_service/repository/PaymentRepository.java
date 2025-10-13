package com.samsung.payment_service.repository;

import com.samsung.payment_service.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    boolean existsByOrderId(String orderId);

    Optional<Payment> findByOrderId(String orderId);
}
