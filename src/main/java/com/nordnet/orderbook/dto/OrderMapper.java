package com.nordnet.orderbook.dto;

import com.nordnet.orderbook.model.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .ticker(String.valueOf(order.getTicker()))
                .orderSide(String.valueOf(order.getOrderSide()))
                .volume(order.getVolume())
                .price(order.getPrice())
                .currency(order.getCurrency())
                .created(order.getCreated())
                .build();
    }

    public Order toEntity(OrderRequest orderRequest, Long id) {
        return Order.builder()
                .id(id)
                .ticker(orderRequest.ticker())
                .orderSide(orderRequest.orderSide())
                .volume(orderRequest.volume())
                .price(orderRequest.price())
                .currency(orderRequest.currency())
                .created(LocalDate.now())
                .build();
    }
}
