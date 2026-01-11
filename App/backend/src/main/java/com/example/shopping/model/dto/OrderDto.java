package com.example.shopping.model.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString
public class OrderDto {

    @ToString.Exclude
    @JsonManagedReference
    List<OrderedProductDto> orderedProductDtoList;


    String toto;

}
