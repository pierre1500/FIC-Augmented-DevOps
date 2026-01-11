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

import java.util.*;


@Service
@Slf4j
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductInStockRepository productInStockRepository;

    @Autowired
    private StockEventRepository stockEventRepository;

    private final static String DEFAULT_STOCK_NAME = "Default Stock";

    public List<Stock> getAllStock(){
        return stockRepository.findAll();
    }

    @Transactional
    public Stock getStockByIdOrCreateDefaultStock(long id){
        var result = stockRepository.findById(id);
        Stock resultStock = result.orElse(null);
        if (result.isEmpty()){
            Stock defaultStock = new Stock();
            defaultStock.setStockName(DEFAULT_STOCK_NAME);
            return stockRepository.save(defaultStock);
        }
        return resultStock;
    }

    @Transactional(rollbackFor = Exception.class)
    public void performStockAdjustment(Stock stock, Order order){
        Map<Product, Integer> productInStockQtyMap = new HashMap<>();
        Map<Product, ProductInStock> productInStockMap = new HashMap<>();
        for (ProductInStock prod : stock.getProductList()){
            Product product = prod.getProduct();
            int value = 0;
            if (productInStockQtyMap.containsKey(product)){
                value = productInStockQtyMap.get(product);
            }
            value += prod.getQuantity();
            productInStockQtyMap.put(prod.getProduct(), value);
            productInStockMap.put(prod.getProduct(), prod);
        }

        for (OrderedProduct ps : order.getOrderedProductList()){
            Product product = ps.getProduct();
            int value = 0;
            if (productInStockQtyMap.containsKey(product)){
                value = productInStockQtyMap.get(product);
            }
            value += ps.getQuantity();
            productInStockQtyMap.put(ps.getProduct(), value);
        }

        //stock.setProductList(null);

        //stockRepository.save(stock);
        for (Map.Entry<Product, Integer> entry : productInStockQtyMap.entrySet()){
            Product product = entry.getKey();
            int value = entry.getValue();

            ProductInStock productInStock;
            if (productInStockMap.containsKey(product)){
                ProductInStock obj = productInStockMap.get(product);
                productInStock = productInStockRepository.findById(obj.getId()).orElseThrow(() -> new IllegalArgumentException("Product In Stock not found"));
            } else {
                productInStock = new ProductInStock();
            }

            Product p = productRepository.findById(product.getId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));
            Stock s = stockRepository.findById(stock.getId()).orElseThrow(() -> new IllegalArgumentException("stock not found"));
            productInStock.setProduct(p);
            productInStock.setStock(s);
            productInStock.setQuantity(value);
            productInStockRepository.save(productInStock);
        }

        log.info("Stock adjusted successfully for order {}", order.getId());

    }

}
