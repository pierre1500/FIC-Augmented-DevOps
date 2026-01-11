package com.example.shopping.repository;

import com.example.shopping.model.StockEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockEventRepository extends JpaRepository<StockEvent, Long> {
    // Additional query methods can be defined here if needed
}