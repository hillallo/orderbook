package com.nordnet.orderbook.dto;

import com.nordnet.orderbook.model.OrderSide;
import com.nordnet.orderbook.model.Ticker;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

import java.util.Currency;

@Builder
public record OrderRequest(

        @NotNull
        @Schema(description = "Ticker of the stock (GME,TSLA,SAVE)", example = "SAVE")
        Ticker ticker,

        @NotNull
        @Schema(description = "Side of the order, e.g., BUY or SELL", example = "BUY")
        OrderSide orderSide,

        @NotNull
        @Positive
        @Schema(description = "Number of shares", example = "2")
        int volume,

        @NotNull
        @Positive
        @Schema(description = "Ask/bid price of the order", example = "29.99")
        double price,

        @NotNull
        @Schema(description = "Currency code for the order", example = "SEK")
        Currency currency

) {}