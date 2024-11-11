package com.nordnet.orderbook.dto;

import lombok.Builder;

@Builder
public record OrderSummaryDTO(
        String ticker,
        String orderSide,
        double averagePrice,
        double maxPrice,
        double minPrice,
        int totalOrders
) {}