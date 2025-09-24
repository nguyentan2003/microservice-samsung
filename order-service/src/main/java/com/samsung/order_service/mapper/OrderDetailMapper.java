package com.samsung.order_service.mapper;


import com.samsung.order_service.dto.request.OrderDetailCreationRequest;
import com.samsung.order_service.dto.response.OrderDetailResponse;

import com.samsung.order_service.entity.OrderDetail;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetail toOrderDetail(OrderDetailCreationRequest request);

    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
    List<OrderDetailResponse> toListOrderDetailResponse(List<OrderDetail> orderDetails);
}
