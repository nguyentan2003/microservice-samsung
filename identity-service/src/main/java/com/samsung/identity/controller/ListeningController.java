package com.samsung.identity.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.samsung.data_static.Topic;
import com.samsung.event.dto.DataOrderCreated;
import com.samsung.event.dto.DataUserInfo;
import com.samsung.identity.mapper.UserMapper;
import com.samsung.identity.service.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ListeningController {
    private final UserService userService;
    private final UserMapper userMapper;

    private final KafkaTemplate<String, Object> kafkaObject;

    @KafkaListener(topics = Topic.CUSTOMER_SUMMARY_ORDER_CREATED)
    public void PushDataOrderCreated(DataOrderCreated dataOrderCreated) {
        DataUserInfo dataUserInfo = userService.getDataInfo(dataOrderCreated.getUserId());
        dataUserInfo.setOrderId(dataOrderCreated.getOrderId());

        kafkaObject.send(Topic.PUSH_DATA_INFO_ORDER_CREATED, dataOrderCreated.getOrderId(), dataUserInfo);
        log.info("gui data user info for customer summary create : {}", dataUserInfo);
    }
}
