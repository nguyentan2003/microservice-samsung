package com.samsung.product_service.controller;

import com.samsung.event.dto.DataOrderCreated;
import com.samsung.event.dto.DataOrderProduct;
import com.samsung.event.dto.DataPushOrderSuccess;
import com.samsung.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor

@Slf4j
public class ListenKafkaController {
    private final ProductService productService;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Object> kafkaTemplateObject;

    @KafkaListener(topics = "OrderCreated")
    public void listeningOrderCreated(DataOrderCreated event){
        if(productService.checkStock(event)){
            kafkaTemplate.send("OrderStockReserved",event.getOrderId(),event.getOrderId());
        }else {
            kafkaTemplate.send("OrderStockFailed",event.getOrderId(),event.getOrderId());
            log.info("het hang mot so san pham");
        }
    }
    @KafkaListener(topics = "ReturnStock")
    public void listeningReturnStock(String orderId){
        if(productService.returnStockByOrderId(orderId)){
            log.info("return stock {} thanh cong",orderId);
        }else {
            log.info("return stock {} fail",orderId);
        }
    }

    @KafkaListener(topics = "PushDataOrderSuccess3")
    public void PushDataOrderSuccess(DataPushOrderSuccess dataPushOrderSuccess){

        DataOrderProduct dataOrderProduct = DataOrderProduct.builder()
                .orderItemProducts( productService.getDataOrderProduct(dataPushOrderSuccess.getOrderId()))
                .orderId(dataPushOrderSuccess.getOrderId())
                .build();

        kafkaTemplateObject.send("OrderSuccessProduct3",dataPushOrderSuccess.getOrderId(),dataOrderProduct);
        log.info("gui data product for customer summary success : {}",dataOrderProduct);
    }

}
