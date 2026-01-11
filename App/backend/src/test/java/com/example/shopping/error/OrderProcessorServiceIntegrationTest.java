package com.example.shopping.error;

import com.example.shopping.model.Product;
import com.example.shopping.model.dto.OrderDto;
import com.example.shopping.model.dto.OrderedProductDto;
import com.example.shopping.model.dto.ProductDto;
import com.example.shopping.repository.ProductInStockRepository;
import com.example.shopping.repository.ProductRepository;
import com.example.shopping.repository.StockEventRepository;
import com.example.shopping.service.InitService;
import com.example.shopping.service.OrderProcessorService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(OutputCaptureExtension.class)
class OrderProcessorServiceIntegrationTest {

    @Autowired
    private OrderProcessorService orderProcessorService;

    @Autowired
    private StockEventRepository stockEventRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductInStockRepository productInStockRepository;


    private Product sampleProduct;

    @Autowired
    private InitService initService;

    @BeforeEach
    public void initData(){
        initService.initData();
        sampleProduct = productRepository.findAll().get(0);
    }

    @Test
    void testPersistOrderAndAdjustStock() {

        var testInit = productInStockRepository.findAll();

        Assertions.assertEquals(5, testInit.size());

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
        Assertions.assertEquals("SUCCESS", result.get(0).getStatus());
    }

}