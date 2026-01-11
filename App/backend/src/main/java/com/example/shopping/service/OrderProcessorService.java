package com.example.shopping.service;


import com.example.shopping.model.Order;
import com.example.shopping.model.Stock;
import com.example.shopping.model.StockEvent;
import com.example.shopping.model.dto.OrderDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;


@Service
@Slf4j
public class OrderProcessorService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockService stockService;

    @Autowired
    private StockEventService stockEventService;

    @Transactional
    public void persistOrderAndAdjustStock(OrderDto order){
        //Get current stock
        Stock currentStock = stockService.getStockByIdOrCreateDefaultStock(1L);
        //Persist order
        var orderEntity = orderService.persistOrder(order);

        StockEvent event = stockEventService.createBaseStockEvent();

        //Adjust stock with order
        adjustStockWithOrder(currentStock, orderEntity, event);

    }


    @Async
    public CompletableFuture<Void> adjustStockWithOrder(Stock stock, Order order, StockEvent event){

        return CompletableFuture.runAsync(() -> {
            log.debug("adjustStockWithOrder for order {}", order.getId());
            try {
                stockService.performStockAdjustment(stock, order);
                event.setEventTime(LocalDateTime.now());
                event.setStatus("SUCCESS");
            } catch (Exception e) {
                log.error("Error during stock adjustement : {}", e.getMessage());
                event.setEventTime(LocalDateTime.now());
                event.setStatus("FAIL");
            }
        });

    }



}
