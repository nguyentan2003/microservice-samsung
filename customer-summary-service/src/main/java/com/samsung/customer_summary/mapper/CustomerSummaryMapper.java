package com.samsung.customer_summary.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.samsung.customer_summary.entity.OrderItemSummary;
import com.samsung.event.dto.ItemDetail;
import com.samsung.event.dto.OrderItemProduct;

@Mapper(componentModel = "spring")
public interface CustomerSummaryMapper {
    OrderItemSummary toOrderItemSummary(OrderItemProduct orderItemProduct);

    // List<OrderItemSummary> toOrderItemSummaries(List<OrderItemProduct> orderItemProducts);
    List<OrderItemSummary> toOrderItemSummaries(List<ItemDetail> list);
}
