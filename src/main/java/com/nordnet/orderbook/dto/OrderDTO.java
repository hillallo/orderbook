package com.nordnet.orderbook.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Currency;

@Getter
public class OrderDTO {

    private final Long id;
    private final String ticker;
    private final String orderSide;
    private final int volume;
    private final double price;
    private final Currency currency;
    private final LocalDate created;

    @Builder
    public OrderDTO(Long id,
                    String ticker,
                    String orderSide,
                    int volume,
                    double price,
                    Currency currency,
                    LocalDate created) {
        this.id = id;
        this.ticker = ticker;
        this.orderSide = orderSide;
        this.volume = volume;
        this.price = price;
        this.currency = currency;
        this.created = created;
    }
}
