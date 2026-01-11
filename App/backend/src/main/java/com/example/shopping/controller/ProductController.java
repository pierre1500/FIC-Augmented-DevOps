package com.example.shopping.controller;

import com.example.shopping.service.ProductService;
import com.example.shopping.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow only specific origin
public class ProductController {

    @Autowired
    public ProductService productService;

    @Autowired
    public StockService stockService;

    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<?> getStock(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(stockService.getStockByIdOrCreateDefaultStock(Long.parseLong(id)));
    }


}