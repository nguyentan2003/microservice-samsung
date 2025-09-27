package com.samsung.order_service.service;

import com.samsung.event.dto.ItemDetail;
import com.samsung.event.dto.OrderCreatedEvent;
import com.samsung.event.dto.OrderStockStatus;
import com.samsung.order_service.dto.request.OrderCreationRequest;
import com.samsung.order_service.dto.request.OrderDetailCreationRequest;
import com.samsung.order_service.dto.response.OrderDetailResponse;
import com.samsung.order_service.dto.response.OrderResponse;
import com.samsung.order_service.entity.Order;
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

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
     TaskScheduler taskScheduler; // Dùng Spring TaskScheduler

    KafkaTemplate<String, Object> kafkaTemplate;

    public OrderResponse createOrder(OrderCreationRequest request){
        Order order = orderMapper.toOrder(request);
        order.setStatus("PENDING");
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

            // push event vao kafka
            OrderCreatedEvent event = OrderCreatedEvent.builder()
                    .orderId(order.getId())
                    .userId(order.getUserId())
                    .listItemDetail(itemDetails)
                    .totalAmount(order.getTotalAmount())
                    .build();

            // push message order-create
            kafkaTemplate.send("order-created3", event);

         //  kiem tra xem order da duoc thanh toan chua
            scheduleOrderTimeout(order.getId());

        } catch (Exception e) {
            throw new AppException(ErrorCode.ERROR_CALL_ORDER_DETAIL);
        }


        return orderResponse;
    }
    private void scheduleOrderTimeout(String orderId) {
        taskScheduler.schedule(() -> {
            Order order = orderRepository.findById(orderId).orElseThrow(null);

            if (order != null) {

                if ("PENDING_PAYMENT".equals(order.getStatus())) {
                    order.setStatus("CANCELED");
                    orderRepository.save(order);
                    OrderStockStatus orderStockStatus = OrderStockStatus.builder()
                            .userId(order.getUserId())
                            .status(false)
                            .orderId(order.getId())
                            .build();
                    kafkaTemplate.send("order-expired", orderStockStatus);
                    log.info("order : "+orderId +" đã bị hủy");
                }
            }
        }, Date.from(Instant.now().plus(1, ChronoUnit.MINUTES)));
    }
    public void CancelOrder(String orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(()->{
            throw new RuntimeException();
        });
        order.setStatus("CANCELED");
        orderRepository.save(order);
    }
    public void TransPendingPayment(String orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(()->{
            throw new RuntimeException();
        });
        order.setStatus("PENDING_PAYMENT");
        orderRepository.save(order);
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
