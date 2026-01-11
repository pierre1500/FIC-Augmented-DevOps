package com.example.shopping.service;

import com.example.shopping.model.*;
import com.example.shopping.repository.ProductInStockRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.repository.StockEventRepository;
import com.example.shopping.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class StockEventService {

    @Autowired
    private StockEventRepository stockEventRepository;

    @Transactional(rollbackFor = Exception.class)
    public StockEvent createBaseStockEvent() {
        StockEvent event = new StockEvent();
        event.setEventTime(LocalDateTime.now());
        event.setStatus("PENDING");

        return stockEventRepository.save(event);
    }

}
