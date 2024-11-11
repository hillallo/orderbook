package com.nordnet.orderbook.controller;

import com.nordnet.orderbook.controller.OrderController;
import com.nordnet.orderbook.dto.OrderMapper;
import com.nordnet.orderbook.dto.OrderSummaryDTO;
import com.nordnet.orderbook.model.Order;
import com.nordnet.orderbook.model.OrderSide;
import com.nordnet.orderbook.model.Ticker;
import com.nordnet.orderbook.service.OrderService;
import com.nordnet.orderbook.dto.OrderRequest;
import com.nordnet.orderbook.dto.OrderDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderMapper orderMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder() {
        OrderRequest request = OrderRequest.builder()
                                            .ticker(Ticker.TSLA)
                                            .orderSide(OrderSide.BUY)
                                            .volume(10)
                                            .price(100.0)
                                            .currency(Currency.getInstance("USD"))
                                            .build();

        OrderDTO expectedResponse = OrderDTO.builder()
                                            .id(1L)
                                            .ticker("TSLA")
                                            .orderSide("BUY")
                                            .volume(10)
                                            .price(100.0)
                                            .currency(Currency.getInstance("USD"))
                                            .build();

        Order mockOrder = Order.builder()
                                .id(1L)
                                .ticker(Ticker.TSLA)
                                .orderSide(OrderSide.BUY)
                                .volume(10)
                                .price(100.0)
                                .currency(Currency.getInstance("USD"))
                                .build();

        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(mockOrder);
        when(orderMapper.toDTO(mockOrder)).thenReturn(expectedResponse);

        ResponseEntity<OrderDTO> response = orderController.createOrder(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGetOrderById_OrderExists() {
        Long orderId = 1L;
        Order mockOrder = Order.builder()
                .id(orderId)
                .ticker(Ticker.TSLA)
                .orderSide(OrderSide.BUY)
                .volume(10)
                .price(100.0)
                .currency(Currency.getInstance("USD"))
                .build();

        OrderDTO expectedResponse = OrderDTO.builder()
                .id(1L)
                .ticker("TSLA")
                .orderSide("BUY")
                .volume(10)
                .price(100.0)
                .currency(Currency.getInstance("USD"))
                .build();

        when(orderService.getOrderById(anyLong())).thenReturn(Optional.of(mockOrder));
        when(orderMapper.toDTO(mockOrder)).thenReturn(expectedResponse);

        ResponseEntity<OrderDTO> response = orderController.getOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGetOrder_OrderDoesNotExist() {
        Long orderId = 1L;

        when(orderService.getOrderById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<OrderDTO> response = orderController.getOrderById(orderId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    public void testGetOrderSummary_Success() {
        Ticker ticker = Ticker.TSLA;
        OrderSide orderSide = OrderSide.BUY;
        LocalDate date = LocalDate.of(2024, 11, 11);

        OrderSummaryDTO expectedSummary = OrderSummaryDTO.builder()
                .ticker(String.valueOf(ticker))
                .orderSide(String.valueOf(orderSide))
                .averagePrice(1)
                .maxPrice(1)
                .minPrice(1)
                .totalOrders(1)
                .build();

        when(orderService.getOrderSummary(any())).thenReturn(expectedSummary);

        ResponseEntity<OrderSummaryDTO> response = orderController.getOrderSummary(ticker, orderSide, date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedSummary, response.getBody());
    }
}
