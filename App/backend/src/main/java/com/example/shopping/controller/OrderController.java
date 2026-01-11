package com.example.shopping.controller;

import com.example.shopping.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow only specific origin
public class OrderController {

    @Autowired
    public OrderService service;

    @GetMapping("/orders")
    public ResponseEntity<?> getOrders() {
        return ResponseEntity.ok().body(service.getAllOrders());
    }

    @GetMapping("/orderedProducts")
    public ResponseEntity<?> getOrderedProduct() {
        return ResponseEntity.ok().body(service.getAllOrderedProduct());
    }

}