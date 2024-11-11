package com.nordnet.orderbook.dto;

import com.nordnet.orderbook.model.OrderSide;
import com.nordnet.orderbook.model.Ticker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record OrderSummaryRequest(
    @NotNull
    Ticker ticker,

    @NotNull
    OrderSide orderSide,

    @NotNull
    LocalDate date
    )
{}
