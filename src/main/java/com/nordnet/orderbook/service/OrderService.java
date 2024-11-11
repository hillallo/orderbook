package com.nordnet.orderbook.service;

import com.nordnet.orderbook.dto.OrderMapper;
import com.nordnet.orderbook.dto.OrderRequest;
import com.nordnet.orderbook.dto.OrderSummaryRequest;
import com.nordnet.orderbook.dto.OrderSummaryDTO;
import com.nordnet.orderbook.model.Order;
import com.nordnet.orderbook.model.OrderSide;
import com.nordnet.orderbook.model.Ticker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {
    private final Map<Long, Order> orders = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();
    private final OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public Order createOrder(OrderRequest orderRequest) {
        Long id = idGenerator.incrementAndGet();
        Order order = orderMapper.toEntity(orderRequest,id);

        orders.put(id, order);
        return order;
    }

    public Optional<Order> getOrderById(Long id) {
        return Optional.ofNullable(orders.get(id));
    }

    public OrderSummaryDTO getOrderSummary(OrderSummaryRequest summaryRequest) {
        Ticker ticker = summaryRequest.ticker();
        OrderSide orderSide = summaryRequest.orderSide();
        LocalDate date = summaryRequest.date();

        List<Order> filteredOrders = orders.values().stream()
                .filter(order -> order.getTicker() == ticker)
                .filter(order -> order.getOrderSide() == orderSide)
                .filter(order -> order.getCreated().isEqual(date))
                .toList();

        double averagePrice = filteredOrders.stream()
                .mapToDouble(Order::getPrice)
                .average()
                .orElse(0.0);

        double maxPrice = filteredOrders.stream()
                .mapToDouble(Order::getPrice)
                .max()
                .orElse(0.0);

        double minPrice = filteredOrders.stream()
                .mapToDouble(Order::getPrice)
                .min()
                .orElse(0.0);

        int totalOrders = filteredOrders.size();

        return OrderSummaryDTO.builder()
                .ticker(ticker.name())
                .orderSide(orderSide.name())
                .averagePrice(averagePrice)
                .maxPrice(maxPrice)
                .minPrice(minPrice)
                .totalOrders(totalOrders)
                .build();
    }

}