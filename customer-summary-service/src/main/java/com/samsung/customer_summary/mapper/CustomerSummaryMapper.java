package com.samsung.customer_summary.mapper;


import com.samsung.customer_summary.entity.OrderItemSummary;
import com.samsung.event.dto.OrderItemProduct;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CustomerSummaryMapper {
    OrderItemSummary toOrderItemSummary(OrderItemProduct orderItemProduct);

    List<OrderItemSummary> toOrderItemSummaries(List<OrderItemProduct> orderItemProducts);
}

