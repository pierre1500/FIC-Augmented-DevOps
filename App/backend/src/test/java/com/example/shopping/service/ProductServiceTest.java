package com.example.shopping.service;

import com.example.shopping.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.InputStream;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadProductReferentialData() {


        var dataInDb = productRepository.findAll();

        Assertions.assertEquals(0, dataInDb.size());

        InputStream dataStream = this.getClass().getResourceAsStream("/product/product.txt");

        //Act
        productService.loadProductReferentialFromFlatFile(dataStream);

        //Assert
        var dataInDbAfter = productRepository.findAll();
        Assertions.assertEquals(2, dataInDbAfter.size());


    }


}