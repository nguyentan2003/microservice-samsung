package com.samsung.shipping_service.mapper;

import com.samsung.shipping_service.dto.response.ShippingResponse;
import com.samsung.shipping_service.entity.Shipping;
import com.samsung.shipping_service.dto.request.ShippingRequest;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ShippingMapper {
    ShippingResponse toShippingResponse(Shipping shipping);
    Shipping toShipping(ShippingRequest request);
    List<ShippingResponse> toShippingResponses(List<Shipping> shippings);
}
