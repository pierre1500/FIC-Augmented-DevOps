package com.example.shopping.config;

import com.example.shopping.service.InitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private InitService initService;

    @Override
    public void run(String... args) {
        try {
            log.info("Initializing sample data...");
            initService.initData();
            log.info("Sample data initialized successfully!");
        } catch (Exception e) {
            log.warn("Data initialization skipped (may already exist): {}", e.getMessage());
        }
    }
}
