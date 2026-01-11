package com.example.shopping.controller;

import com.example.shopping.model.dto.OrderDto;
import com.example.shopping.service.OrderProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow only specific origin
public class ShoppingListController {

    @Autowired
    public OrderProcessorService orderProcessorService;

    @PostMapping(path="/validate")
    public ResponseEntity<?> validateOrder(@RequestBody OrderDto order) {
        orderProcessorService.persistOrderAndAdjustStock(order);

        // Return a JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Order validated successfully");

        return ResponseEntity.ok().body(response);
    }

}