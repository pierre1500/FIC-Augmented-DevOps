package com.example.shopping.repository;

import com.example.shopping.model.ProductInStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInStockRepository extends JpaRepository<ProductInStock, Long> {
    // Additional query methods can be defined here if needed
}