package com.example.shopping.repository;

import com.example.shopping.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Additional query methods can be defined here if needed
    // For example, to find products by name:
    List<Product> findByProductName(String name);

}