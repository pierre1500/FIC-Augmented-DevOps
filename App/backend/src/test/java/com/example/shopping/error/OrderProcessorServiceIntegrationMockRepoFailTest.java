package com.example.shopping.error;

import com.example.shopping.model.Order;
import com.example.shopping.model.Product;
import com.example.shopping.model.Stock;
import com.example.shopping.model.StockEvent;
import com.example.shopping.model.dto.OrderDto;
import com.example.shopping.model.dto.OrderedProductDto;
import com.example.shopping.model.dto.ProductDto;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.repository.StockEventRepository;
import com.example.shopping.service.InitService;
import com.example.shopping.service.OrderProcessorService;
import com.example.shopping.service.StockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(OutputCaptureExtension.class)
class OrderProcessorServiceIntegrationMockRepoFailTest {

    @MockitoBean
    private StockService stockService;

    @Autowired
    @InjectMocks
    private OrderProcessorService orderProcessorService;

    @Autowired
    private StockEventRepository stockEventRepository;

    @Autowired
    private ProductRepository productRepository;

    private Product sampleProduct;

    @Autowired
    private InitService initService;

    @BeforeEach
    public void initData(){
        initService.initData();
        sampleProduct = productRepository.findAll().get(0);
    }

    @Test
    void testPersistOrderAndAdjustStockAndFailOnInsert() {
        Stock baseStock = new Stock();
        baseStock.setStockName("Test Stock");

        StockEvent event = new StockEvent();
        event.setStatus("PENDING");
        event.setEventTime(LocalDateTime.now());

        // Arrange
        when(stockService.getStockByIdOrCreateDefaultStock(anyLong())).thenReturn(baseStock);
        doThrow(new ArrayIndexOutOfBoundsException()).when(stockService).performStockAdjustment(any(Stock.class), any(Order.class));

        OrderDto orderDto = new OrderDto();
        ProductDto p1 = new ProductDto();

        p1.setId(sampleProduct.getId());
        p1.setProductName(sampleProduct.getProductName());

        OrderedProductDto orderedProductDto = new OrderedProductDto();
        orderedProductDto.setOrder(orderDto);
        orderedProductDto.setQuantity(3);
        orderedProductDto.setProduct(p1);

        List<OrderedProductDto> orderList = List.of(orderedProductDto);

        orderDto.setOrderedProductDtoList(orderList);
        orderProcessorService.persistOrderAndAdjustStock(orderDto);

        var result = stockEventRepository.findAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("FAIL", result.get(0).getStatus());
    }

}