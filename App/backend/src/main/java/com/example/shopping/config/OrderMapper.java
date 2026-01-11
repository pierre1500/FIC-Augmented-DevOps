package com.example.shopping.config;

import com.example.shopping.model.Order;
import com.example.shopping.model.OrderedProduct;
import com.example.shopping.model.Product;
import com.example.shopping.model.dto.OrderDto;
import com.example.shopping.model.dto.OrderedProductDto;
import com.example.shopping.model.dto.ProductDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper( OrderMapper.class );

    @Mapping(source = "orderedProductList", target = "orderedProductDtoList")
    OrderDto orderToOrderDto(Order order, @Context CycleAvoidingMappingContext context);

    @Mapping(source = "orderedProductDtoList", target = "orderedProductList")
    Order orderDtoToOrder(OrderDto order, @Context CycleAvoidingMappingContext context);

    OrderedProductDto orderProductToOrderProductDto(OrderedProduct order);

    OrderedProduct orderProductDtoToOrderProduct(OrderedProductDto orderProduct);

    ProductDto fromEntity(Product product);

    Product toEntity(ProductDto productDto);

}
