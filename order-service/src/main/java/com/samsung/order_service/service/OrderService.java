package com.samsung.order_service.service;

import com.samsung.data_static.Topic;
import com.samsung.event.dto.DataOrderCreated;
import com.samsung.event.dto.NotificationStatus;
import com.samsung.order_service.dto.request.OrderCreationRequest;
import com.samsung.order_service.dto.response.OrderResponse;
import com.samsung.order_service.entity.Order;

import com.samsung.data_static.OrderStatus;
import com.samsung.order_service.exception.AppException;
import com.samsung.order_service.exception.ErrorCode;
import com.samsung.order_service.mapper.OrderMapper;
import com.samsung.order_service.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        kafkaTemplate.send(Topic.NOTIFICATION_STATUS,order.getId(), NotificationStatus.builder()
                        .message(Topic.ORDER_CREATED)
                        .orderId(order.getId())
                        .userId(order.getUserId())
                .build());

        scheduleOrderTimeout(order.getId());
        return orderResponse;
    }

    public Boolean cancelOrder(String orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(()->{
            throw new AppException(ErrorCode.ORDER_NOT_EXISTED);
        });
        String status = order.getStatus();
        String type = order.getPaymentType();
        if(status.equals(OrderStatus.CANCELED) || status.equals(OrderStatus.SHIPPING)){
            throw new AppException(ErrorCode.STATUS_NOT_MATCH);
        }
        else  {

            order.setStatus(OrderStatus.CANCELED);
            orderRepository.save(order);
            NotificationStatus notificationStatus1 = NotificationStatus.builder()
                    .userId(order.getUserId())
                    .orderId(orderId)
                    .build();

            if (status.equals(OrderStatus.STOCK_RESERVED)){
                kafkaTemplateString.send(Topic.RETURN_STOCK,orderId,orderId);
                notificationStatus1.setMessage(Topic.RETURN_STOCK);
                kafkaTemplate.send(Topic.NOTIFICATION_STATUS,orderId,notificationStatus1);
            }else if(status.equals(OrderStatus.PAYMENT_SUCCESS)){
                kafkaTemplateString.send(Topic.REFUND_MONEY,orderId,orderId);
                notificationStatus1.setMessage(Topic.REFUND_MONEY);
                kafkaTemplate.send(Topic.NOTIFICATION_STATUS,orderId,notificationStatus1);
            }
            else if (status.equals(OrderStatus.SUCCESS)){
                kafkaTemplateString.send(Topic.RETURN_STOCK,orderId,orderId);
                notificationStatus1.setMessage(Topic.RETURN_STOCK);
                kafkaTemplate.send(Topic.NOTIFICATION_STATUS,orderId,notificationStatus1);
                if(type.equals("PREPAID")){
                    kafkaTemplateString.send(Topic.REFUND_MONEY,orderId,orderId);
                    notificationStatus1.setMessage(Topic.REFUND_MONEY);
                    kafkaTemplate.send(Topic.NOTIFICATION_STATUS,orderId,notificationStatus1);
                }
            }
        }
        return true;
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

                    kafkaTemplate.send(Topic.NOTIFICATION_STATUS,order.getId(), NotificationStatus.builder()
                            .message(Topic.ORDER_TIME_OUT)
                            .orderId(order.getId())
                            .userId(order.getUserId())
                            .build());

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

    public Order updateOrderStatusAdmin(String orderId,String status){
        Order order = getOrderById(orderId);

        if(order != null) {
            if(status.equals(OrderStatus.CANCELED)){
                cancelOrder(orderId);
            }
            order.setStatus(status);

            return orderRepository.save(order);

        }
        else throw new AppException(ErrorCode.ORDER_NOT_EXISTED);
    }


}
