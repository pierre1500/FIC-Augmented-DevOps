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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Slf4j
public class InitService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductInStockRepository productInStockRepository;

    @Autowired
    private StockEventRepository stockEventRepository;

    @Transactional
    public void initData(){


        Product coca = Product.builder()
                .productName("Coca")
                .productPrice(10)
                .build();

        Product chips = Product.builder()
                .productName("'Chips'")
                .productPrice(5.50)
                .build();

        Product vin = Product.builder()
                .productName("Vin")
                .productPrice(15)
                .build();

        Product vodka = Product.builder()
                .productName("Vodka")
                .productPrice(25)
                .build();

        Product juice = Product.builder()
                .productName("Juice")
                .productPrice(8)
                .build();

        Product water = Product.builder()
                .productName("Water")
                .productPrice(2)
                .build();

        Product beer = Product.builder()
                .productName("Beer")
                .productPrice(12)
                .build();

        productRepository.saveAllAndFlush(List.of(coca, chips, vin, vodka, juice, water, beer));


        Stock stock = Stock.builder()
                .stockName("Default Stock")
                .build();
        stock = stockRepository.save(stock);

        ProductInStock wineInStock = ProductInStock.builder()
                .product(vin)
                .stock(stock)
                .quantity(10)
                .build();

        ProductInStock vodkaInStock = ProductInStock.builder()
                .product(vodka)
                .stock(stock)
                .quantity(20)
                .build();

        ProductInStock juiceInStock = ProductInStock.builder()
                .product(juice)
                .stock(stock)
                .quantity(15)
                .build();

        ProductInStock waterInStock = ProductInStock.builder()
                .product(water)
                .stock(stock)
                .quantity(50)
                .build();

        ProductInStock beerInStock = ProductInStock.builder()
                .product(beer)
                .stock(stock)
                .quantity(30)
                .build();

        List<ProductInStock> prodInStock = new ArrayList<>();
        prodInStock.add(wineInStock);
        prodInStock.add(vodkaInStock);
        prodInStock.add(juiceInStock);
        prodInStock.add(waterInStock);
        prodInStock.add(beerInStock);

        productInStockRepository.saveAll(prodInStock);

        stock.setProductList(prodInStock);
        stock = stockRepository.save(stock);
    }

}
