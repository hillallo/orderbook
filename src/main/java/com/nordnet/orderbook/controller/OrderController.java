package com.nordnet.orderbook.controller;

import com.nordnet.orderbook.dto.*;
import com.nordnet.orderbook.model.Order;
import com.nordnet.orderbook.model.OrderSide;
import com.nordnet.orderbook.model.Ticker;
import com.nordnet.orderbook.service.OrderService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderRequest orderRequest) {

        Order order = orderService.createOrder(orderRequest);
        OrderDTO orderDTO = orderMapper.toDTO(order);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(   @Schema(description = "Unique Identifier of order e.g. Long(1)")
                                                    @PathVariable Long id) {
        Optional<Order> optionalOrder = orderService.getOrderById(id);

        if (optionalOrder.isPresent()) {
            OrderDTO orderDTO = orderMapper.toDTO(optionalOrder.get());
            return ResponseEntity.ok(orderDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<OrderSummaryDTO> getOrderSummary(
            @Schema(description = "Ticker of the stock (GME,TSLA,SAVE)", example = "SAVE")
            @RequestParam("ticker") Ticker ticker,

            @Schema(description = "Side of the order, e.g., BUY or SELL", example = "BUY")
            @RequestParam("orderSide") OrderSide orderSide,

            @RequestParam("summaryDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Schema(description = "Summary date e.g. 2024-11-11", example = "2024-11-11")
            LocalDate date
    ) {
        OrderSummaryRequest summaryRequest = OrderSummaryRequest.builder()
                                                                .ticker(ticker)
                                                                .orderSide(orderSide)
                                                                .date(date)
                                                                .build();
        OrderSummaryDTO summaryResponse = orderService.getOrderSummary(summaryRequest);

        return ResponseEntity.ok(summaryResponse);
    }
}
