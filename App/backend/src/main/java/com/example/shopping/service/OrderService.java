package com.example.shopping.service;

import com.example.shopping.config.CycleAvoidingMappingContext;
import com.example.shopping.config.OrderMapper;
import com.example.shopping.model.*;
import com.example.shopping.model.dto.OrderDto;
import com.example.shopping.model.dto.OrderedProductDto;
import com.example.shopping.repository.OrderRepository;
import com.example.shopping.repository.OrderedProductRepository;
import com.example.shopping.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderedProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order persistOrder(OrderDto orderDto){
        Order order = OrderMapper.INSTANCE.orderDtoToOrder( orderDto, new CycleAvoidingMappingContext());
        var newOrder = orderRepository.save(order);
        List<OrderedProduct> ordProd = new ArrayList<>();
        for (OrderedProductDto orderedProductDto : orderDto.getOrderedProductDtoList()) {
            OrderedProduct orderedProduct = OrderMapper.INSTANCE.orderProductDtoToOrderProduct(orderedProductDto);
            orderedProduct.setOrder(order);
            List<Product> prodList = productRepository.findByProductName(orderedProductDto.getProduct().getProductName());
            if (prodList != null && prodList.size() > 0){
                Product prod = prodList.get(0);
                orderedProduct.setProduct(prod);
                ordProd.add(orderProductRepository.save(orderedProduct));
            }

        }
        newOrder.setOrderedProductList(ordProd);
        return newOrder;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<OrderedProduct> getAllOrderedProduct(){
        return orderProductRepository.findAll();
    }

}
