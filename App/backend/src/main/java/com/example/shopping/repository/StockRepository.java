package com.example.shopping.repository;

import com.example.shopping.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    // Additional query methods can be defined here if needed
}