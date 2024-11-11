package com.nordnet.orderbook.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Currency;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;

        Ticker ticker;

        @Enumerated(EnumType.STRING)
        OrderSide orderSide;

        int volume;

        double price;

        Currency currency;

        LocalDate created;

}


