package com.samsung.customer_summary.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsung.customer_summary.entity.CustomerSummary;
import com.samsung.customer_summary.entity.PendingMessage;
import com.samsung.customer_summary.repository.CustomerSummaryRepository;
import com.samsung.customer_summary.repository.PendingMessageRepository;
import com.samsung.data_static.OrderStatus;
import com.samsung.data_static.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PendingMessageProcessor {
    private final PendingMessageRepository pendingRepo;
    private final MongoTemplate mongoTemplate;
    private final CustomerSummaryRepository customerSummaryRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // chạy lại mỗi 3 giây
    @Scheduled(fixedDelay = 3000)
    public void processPendingMessages() {
        List<PendingMessage> list = pendingRepo.findAll();
        for (PendingMessage msg : list) {
            try {
                CustomerSummary cs = customerSummaryRepository.findByOrderId(msg.getOrderId()).orElse(null);
                if (cs == null) {
                    msg.setRetryCount(msg.getRetryCount() + 1);

                    // nếu retry quá 10 lần thì bỏ qua
                    if (msg.getRetryCount() > 10) {
                        log.warn("⚠️ Bỏ qua message {} orderId={} sau 10 lần retry", msg.getType(), msg.getOrderId());
                        pendingRepo.delete(msg);
                        continue;
                    }

                    pendingRepo.save(msg);
                    continue;
                }

                // ✅ Nếu CustomerSummary đã tồn tại, tiến hành xử lý message
                switch (msg.getType()) {

                    // ------------------ DATA PRODUCT ------------------
                    case Topic.ORDER_PRODUCT_DATA:
                        var dataProduct = objectMapper.convertValue(msg.getPayload(), com.samsung.event.dto.DataOrderProduct.class);
                        Update updateProduct = new Update()
                                .set("orderItemSummaries", dataProduct.getOrderItemProducts())
                                .set("updatedAt", LocalDateTime.now());
                        mongoTemplate.updateFirst(new Query(Criteria.where("orderId").is(dataProduct.getOrderId())),
                                updateProduct, CustomerSummary.class);
                        log.info("✅ Retry Update data product orderId={}", dataProduct.getOrderId());
                        break;

                    // ------------------ USER INFO ------------------
                    case Topic.PUSH_DATA_INFO_ORDER_CREATED:
                        var dataUserInfo = objectMapper.convertValue(msg.getPayload(), com.samsung.event.dto.DataUserInfo.class);
                        Update updateUserInfo = new Update()
                                .set("username", dataUserInfo.getUsername())
                                .set("fullName", dataUserInfo.getFullName())
                                .set("email", dataUserInfo.getEmail())
                                .set("phone", dataUserInfo.getPhone())
                                .set("address", dataUserInfo.getAddress())
                                .set("updatedAt", LocalDateTime.now());
                        mongoTemplate.updateFirst(new Query(Criteria.where("orderId").is(dataUserInfo.getOrderId())),
                                updateUserInfo, CustomerSummary.class);
                        log.info("✅ Retry Update user info cho orderId={}", dataUserInfo.getOrderId());
                        break;

                    // ------------------ PAYMENT SUCCESS ------------------
                    case Topic.DATA_PAYMENT_SUCCESS:
                        var dataPayment = objectMapper.convertValue(msg.getPayload(), com.samsung.event.dto.DataPayment.class);
                        Update updatePayment = new Update()
                                .set("paymentMethod", dataPayment.getPaymentMethod())
                                .set("totalAmount", dataPayment.getAmount())
                                .set("paymentStatus", OrderStatus.PAYMENT_SUCCESS)
                                .set("transactionId", dataPayment.getTransactionId())
                                .set("paymentTime", dataPayment.getPaymentTime())
                                .set("updatedAt", LocalDateTime.now());
                        mongoTemplate.updateFirst(new Query(Criteria.where("orderId").is(dataPayment.getOrderId())),
                                updatePayment, CustomerSummary.class);
                        log.info("✅ Retry Update payment success cho orderId={}", dataPayment.getOrderId());
                        break;

                    // ------------------ REFUND MONEY ------------------
                    case Topic.REFUND_MONEY:
                        String refundOrderId = objectMapper.convertValue(msg.getPayload(), String.class);
                        Update updateRefund = new Update()
                                .set("paymentStatus", OrderStatus.REFUND_MONEY)
                                .set("updatedAt", LocalDateTime.now());
                        mongoTemplate.updateFirst(new Query(Criteria.where("orderId").is(refundOrderId)),
                                updateRefund, CustomerSummary.class);
                        log.info("✅ Retry Update refund money cho orderId={}", refundOrderId);
                        break;

                    // ------------------ PAYMENT FAILED ------------------
                    case Topic.PAYMENT_FAILED:
                        String failedOrderId = objectMapper.convertValue(msg.getPayload(), String.class);
                        Update updateFailed = new Update()
                                .set("paymentStatus", OrderStatus.PAYMENT_FAILED)
                                .set("updatedAt", LocalDateTime.now());
                        mongoTemplate.updateFirst(new Query(Criteria.where("orderId").is(failedOrderId)),
                                updateFailed, CustomerSummary.class);
                        log.info("✅ Retry Update payment failed cho orderId={}", failedOrderId);
                        break;

                    // ------------------ STOCK RESERVED ------------------
                    case Topic.ORDER_STOCK_RESERVED:
                        String reservedOrderId = objectMapper.convertValue(msg.getPayload(), String.class);
                        Update updateStockReserved = new Update()
                                .set("statusStock", OrderStatus.STOCK_RESERVED)
                                .set("updatedAt", LocalDateTime.now());
                        mongoTemplate.updateFirst(new Query(Criteria.where("orderId").is(reservedOrderId)),
                                updateStockReserved, CustomerSummary.class);
                        log.info("✅ Retry Update stock reserved cho orderId={}", reservedOrderId);
                        break;

                    // ------------------ RETURN STOCK ------------------
                    case Topic.RETURN_STOCK:
                        String returnOrderId = objectMapper.convertValue(msg.getPayload(), String.class);
                        Update updateReturnStock = new Update()
                                .set("statusStock", OrderStatus.RETURN_STOCK)
                                .set("updatedAt", LocalDateTime.now());
                        mongoTemplate.updateFirst(new Query(Criteria.where("orderId").is(returnOrderId)),
                                updateReturnStock, CustomerSummary.class);
                        log.info("✅ Retry Update return stock cho orderId={}", returnOrderId);
                        break;

                    // ------------------ STOCK FAILED ------------------
                    case Topic.ORDER_STOCK_FAILED:
                        String stockFailedOrderId = objectMapper.convertValue(msg.getPayload(), String.class);
                        Update updateStockFailed = new Update()
                                .set("statusStock", OrderStatus.STOCK_FAILED)
                                .set("updatedAt", LocalDateTime.now());
                        mongoTemplate.updateFirst(new Query(Criteria.where("orderId").is(stockFailedOrderId)),
                                updateStockFailed, CustomerSummary.class);
                        log.info("✅ Retry Update stock failed cho orderId={}", stockFailedOrderId);
                        break;

                    // ------------------ ORDER SUCCESS ------------------
                    case Topic.ORDER_SUCCESS:
                        var dataOrderSuccess = objectMapper.convertValue(msg.getPayload(), com.samsung.event.dto.DataOrder.class);
                        Update updateOrderSuccess = new Update()
                                .set("orderStatus", OrderStatus.SUCCESS)
                                .set("updatedAt", LocalDateTime.now());
                        mongoTemplate.updateFirst(new Query(Criteria.where("orderId").is(dataOrderSuccess.getId())),
                                updateOrderSuccess, CustomerSummary.class);
                        log.info("✅ Retry Update order success cho orderId={}", dataOrderSuccess.getId());
                        break;

                    // ------------------ ORDER CANCELED ------------------
                    case Topic.ORDER_CANCELED:
                        var dataOrderCanceled = objectMapper.convertValue(msg.getPayload(), com.samsung.event.dto.DataOrder.class);
                        Update updateOrderCanceled = new Update()
                                .set("orderStatus", OrderStatus.CANCELED)
                                .set("updatedAt", LocalDateTime.now());
                        mongoTemplate.updateFirst(new Query(Criteria.where("orderId").is(dataOrderCanceled.getId())),
                                updateOrderCanceled, CustomerSummary.class);
                        log.info("✅ Retry Update order canceled cho orderId={}", dataOrderCanceled.getId());
                        break;

                    default:
                        log.warn("⚠️ Không xử lý được message type={} cho orderId={}", msg.getType(), msg.getOrderId());
                        break;
                }

                // ✅ Xử lý thành công thì xóa message khỏi pending
                pendingRepo.delete(msg);

            } catch (Exception e) {
                log.error("❌ Retry thất bại message={} orderId={} - {}", msg.getType(), msg.getOrderId(), e.getMessage());
            }
        }
    }
}
