package com.example.shopping.controller;

import com.example.shopping.service.InitService;
import com.example.shopping.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = "http://localhost:4200") // Allow only specific origin
@Slf4j
public class DataIntegrationController {

    @Autowired
    public ProductService productService;

    @Autowired
    public InitService initService;

    @PostMapping("/init")
    public ResponseEntity<String> initDataInApplication() {
        try {
            initService.initData();
        } catch(Exception e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/upload/products")
    public ResponseEntity<String> uploadProductReferentialFromFile(@RequestParam("file") MultipartFile file) {
        try {
            productService.loadProductReferentialFromFlatFile(file.getInputStream());
        } catch(Exception e) {
            log.error(e.getMessage());
        }
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/upload/stock")
    public ResponseEntity<String> uploadStockFromFile(@RequestParam("file") MultipartFile file) {
        // TODO : Implement stock upload
        return ResponseEntity.ok().body("");
    }
}