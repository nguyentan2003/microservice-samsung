package com.samsung.order_service.service;

import com.samsung.order_service.dto.request.ItemDetail;
import com.samsung.order_service.dto.request.OrderCreationRequest;
import com.samsung.order_service.dto.request.OrderDetailCreationRequest;
import com.samsung.order_service.dto.response.OrderDetailResponse;
import com.samsung.order_service.dto.response.OrderResponse;
import com.samsung.order_service.entity.Order;
import com.samsung.order_service.entity.OrderDetail;
import com.samsung.order_service.exception.AppException;
import com.samsung.order_service.exception.ErrorCode;

import com.samsung.order_service.mapper.OrderDetailMapper;
import com.samsung.order_service.mapper.OrderMapper;
import com.samsung.order_service.repository.OrderDetailRepository;
import com.samsung.order_service.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderService {
    private final OrderDetailRepository orderDetailRepository;
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    OrderDetailService orderDetailService;
    OrderDetailMapper orderDetailMapper;

    public OrderResponse createOrder(OrderCreationRequest request){
        Order order = orderMapper.toOrder(request);
        orderRepository.save(order);
        OrderResponse orderResponse= orderMapper.toOrderResponse(order);

        try {
            List<ItemDetail> itemDetails = request.getListItemDetail();
            List<OrderDetailResponse> orderDetailResponses = new ArrayList<>();
            itemDetails.forEach((item)->{
                OrderDetailCreationRequest orderDetailCreationRequest = OrderDetailCreationRequest.builder()
                        .orderId(order.getId())
                        .priceAtTime(item.getPriceAtTime())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .build();
                OrderDetailResponse orderDetailResponse = orderDetailService.createOrderDetail(orderDetailCreationRequest);
                orderDetailResponses.add(orderDetailResponse);

            });
            orderResponse.setListOrderDetailResponse(orderDetailResponses);

        } catch (Exception e) {
            throw new AppException(ErrorCode.ERROR_CALL_ORDER_DETAIL);
        }


        return orderResponse;
    }


    public List<OrderResponse> getListOrder() {

        List<OrderResponse> listOrderResponses = new ArrayList<>();
              orderRepository.findAll().forEach((order)->{
                  OrderResponse orderResponse = orderMapper.toOrderResponse(order);
                  List<OrderDetailResponse> listOrderDetailList = new ArrayList<>();
                  orderDetailRepository.findAll().forEach((orderDetail)->{
                      if(orderDetail.getOrderId().equals(order.getId())){
                         listOrderDetailList.add(orderDetailMapper.toOrderDetailResponse(orderDetail));
                      }
                  });
                  orderResponse.setListOrderDetailResponse(listOrderDetailList);
                  listOrderResponses.add(orderResponse);
              });

        // Map sang DTO
        return listOrderResponses;
    }

    public OrderResponse getOrderByOrderId(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                ()->{ throw new AppException(ErrorCode.ORDER_NOT_EXISTED);});
        List<OrderDetailResponse> listOrderDetailList = new ArrayList<>();
        orderDetailRepository.findAll().forEach((orderDetail)->{
            if(orderDetail.getOrderId().equals(order.getId())){
               listOrderDetailList.add(orderDetailMapper.toOrderDetailResponse(orderDetail));
            }
        });

        OrderResponse orderResponse =  orderMapper.toOrderResponse(order);
        orderResponse.setListOrderDetailResponse(listOrderDetailList);
        return orderResponse;

    }


}
