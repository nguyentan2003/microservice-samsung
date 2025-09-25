package com.samsung.shipping_service.service;

import com.samsung.shipping_service.dto.response.ShippingResponse;
import com.samsung.shipping_service.repository.ShippingRepository;
import com.samsung.shipping_service.dto.request.ShippingRequest;
import com.samsung.shipping_service.entity.Shipping;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import com.samsung.shipping_service.mapper.ShippingMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ShippingService {

    ShippingMapper shippingMapper;
    ShippingRepository shippingRepository;

    public ShippingResponse createShipping(ShippingRequest request){
        Shipping shipping = shippingMapper.toShipping(request);

        return shippingMapper.toShippingResponse(shippingRepository.save(shipping));
    }

    public List<ShippingResponse> getListShipping(){
        return shippingMapper.toShippingResponses(shippingRepository.findAll());
    }
}
