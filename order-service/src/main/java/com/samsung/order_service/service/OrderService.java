package com.samsung.order_service.service;

import com.samsung.data_static.Topic;
import com.samsung.event.dto.DataOrderCreated;
import com.samsung.order_service.dto.request.OrderCreationRequest;
import com.samsung.order_service.dto.response.OrderResponse;
import com.samsung.order_service.entity.Order;

import com.samsung.data_static.OrderStatus;
import com.samsung.order_service.mapper.OrderMapper;
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
import java.util.Date;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
     TaskScheduler taskScheduler; // Dùng Spring TaskScheduler

    KafkaTemplate<String, Object> kafkaTemplate;
    KafkaTemplate<String, String> kafkaTemplateString;

    public OrderResponse createOrder(OrderCreationRequest request){
        Order order = orderMapper.toOrder(request);
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        OrderResponse orderResponse= orderMapper.toOrderResponse(order);

        DataOrderCreated dataOrderCreated = orderMapper.toDataOrderCreated(request);
        dataOrderCreated.setOrderId(order.getId());
        dataOrderCreated.setStatus(order.getStatus());

        kafkaTemplate.send(Topic.ORDER_CREATED,order.getId(),dataOrderCreated);

        scheduleOrderTimeout(order.getId());
        return orderResponse;
    }

//    scheduleOrderTimeout(order.getId());
    private void scheduleOrderTimeout(String orderId) {
        taskScheduler.schedule(() -> {
            Order order = orderRepository.findById(orderId).orElseThrow(null);

            if (order != null) {

                if (OrderStatus.STOCK_RESERVED.equals(order.getStatus())) {
                    order.setStatus(OrderStatus.CANCELED);
                    orderRepository.save(order);

                    kafkaTemplateString.send(Topic.RETURN_STOCK,orderId, orderId);
                    log.info("order : " +orderId + " đã bị hủy và return stock");
                }
            }
            // 1phut -> 15p
        }, Date.from(Instant.now().plus(1, ChronoUnit.MINUTES)));
    }

    public Order getOrderById(String orderId){
       return orderRepository.findById(orderId).orElse(null);

    }

    public void updateOrderStatus(String orderId,String status){
       Order order = getOrderById(orderId);

       if(order != null) {
           order.setStatus(status);
           orderRepository.save(order);
       }
    }


}
