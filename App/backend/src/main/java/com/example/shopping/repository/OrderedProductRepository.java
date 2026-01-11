package com.example.shopping.repository;

import com.example.shopping.model.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedProductRepository extends JpaRepository<OrderedProduct, Long> {
    // Additional query methods can be defined here if needed
}