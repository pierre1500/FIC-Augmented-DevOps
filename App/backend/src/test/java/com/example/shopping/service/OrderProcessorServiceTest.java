package com.example.shopping.service;

import com.example.shopping.model.Order;
import com.example.shopping.model.Stock;
import com.example.shopping.model.StockEvent;
import com.example.shopping.model.dto.OrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

public class OrderProcessorServiceTest {

    @InjectMocks
    private OrderProcessorService orderProcessorService;

    @Mock
    private OrderService orderService;

    @Mock
    private StockService stockService;

    @Mock
    private StockEventService stockEventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPersistOrderAndAdjustStock() {
        // Arrange
        OrderDto orderDto = new OrderDto();
        Stock stock = new Stock();
        stock.setId(1L);
        Order order = new Order();

        StockEvent event = new StockEvent();

        when(stockService.getStockByIdOrCreateDefaultStock(1L)).thenReturn(stock);
        when(orderService.persistOrder(orderDto)).thenReturn(order);
        when(stockEventService.createBaseStockEvent()).thenReturn(event);

        // Act
        orderProcessorService.persistOrderAndAdjustStock(orderDto);

        // Assert
        verify(stockService, times(1)).getStockByIdOrCreateDefaultStock(1L);
        verify(orderService, times(1)).persistOrder(orderDto);
        verify(stockService, times(1)).performStockAdjustment(stock, order);
    }

    @Test
    void testAdjustStockWithOrder() {
        // Arrange
        Stock stock = new Stock();
        stock.setId(1L);
        Order order = new Order();
        order.setId(123L);

        // Act
        CompletableFuture<Void> future = orderProcessorService.adjustStockWithOrder(stock, order, new StockEvent());

        // Wait for async execution
        future.join();

        // Assert
        verify(stockService, times(1)).performStockAdjustment(stock, order);
    }
}