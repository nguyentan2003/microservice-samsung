package com.samsung.product_service.service;

import com.samsung.product_service.dto.request.OrderDetailCreationRequest;
import com.samsung.product_service.dto.response.OrderDetailResponse;
import com.samsung.product_service.entity.OrderDetail;
import com.samsung.product_service.exception.AppException;
import com.samsung.product_service.exception.ErrorCode;
import com.samsung.product_service.mapper.OrderDetailMapper;
import com.samsung.product_service.repository.OrderDetailRepository;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderDetailService {
    OrderDetailRepository orderDetailRepository;
    OrderDetailMapper orderDetailMapper;

    public OrderDetailResponse createOrderDetail(OrderDetailCreationRequest request) {

        OrderDetail orderDetail = orderDetailMapper.toOrderDetail(request);

        orderDetailRepository.save(orderDetail);

        return orderDetailMapper.toOrderDetailResponse(orderDetail);
    }

    public List<OrderDetailResponse> getListOrderDetail() {

        List<OrderDetailResponse> orderResponses =
                orderDetailMapper.toListOrderDetailResponse(orderDetailRepository.findAll());

        // Map sang DTO
        return orderResponses;
    }

    public OrderDetailResponse getOrderByOrderIdDetail(String orderDetailId) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId).orElseThrow(() -> {
            throw new AppException(ErrorCode.ORDER_DETAIL_NOT_EXISTED);
        });
        return orderDetailMapper.toOrderDetailResponse(orderDetail);
    }
}
