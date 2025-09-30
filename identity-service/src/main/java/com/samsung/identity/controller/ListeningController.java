package com.samsung.identity.controller;

import com.samsung.event.dto.DataPushOrderSuccess;
import com.samsung.event.dto.DataUserInfo;
import com.samsung.identity.mapper.UserMapper;
import com.samsung.identity.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ListeningController {
    private final UserService userService;
    private final UserMapper userMapper;

    private final KafkaTemplate<String, Object> kafkaObject;

    @KafkaListener(topics = "PushDataOrderSuccess3")
    public void PushDataOrderUserInfoSuccess(DataPushOrderSuccess dataPushOrderSuccess){
        DataUserInfo dataUserInfo = userService.getDataInfo(dataPushOrderSuccess.getUserId());
        dataUserInfo.setOrderId(dataPushOrderSuccess.getOrderId());


        kafkaObject.send("OrderSuccessUserInfo3",dataPushOrderSuccess.getOrderId(),dataUserInfo);
        log.info("gui data user info for customer summary success : {}",dataUserInfo);
    }

}

