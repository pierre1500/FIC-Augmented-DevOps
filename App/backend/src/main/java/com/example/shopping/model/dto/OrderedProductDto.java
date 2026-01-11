package com.example.shopping.model.dto;

import com.example.shopping.model.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString
public class OrderedProductDto {


    @JsonBackReference
    @ToString.Exclude
    private OrderDto order;

    private ProductDto product;

    @Column(nullable = false)
    private int quantity;


}
