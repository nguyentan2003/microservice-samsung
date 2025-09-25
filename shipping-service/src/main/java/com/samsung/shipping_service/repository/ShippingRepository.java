package com.samsung.shipping_service.repository;


import com.samsung.shipping_service.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShippingRepository extends JpaRepository<Shipping, String> {
    boolean existsByOrderId(String orderId);

}
