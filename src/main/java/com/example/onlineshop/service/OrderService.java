package com.example.onlineshop.service;

import com.example.onlineshop.entity.dto.OrderProductDto;
import com.example.onlineshop.entity.model.Order;
import com.example.onlineshop.entity.model.Product;
import com.example.onlineshop.entity.model.User;
import com.example.onlineshop.repository.OrderRepository;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public void addOrder(OrderProductDto orderProductDto, UUID id) {
        // System.out.println("IDs from DTO: " + orderProductDto.productsId());

        List<Product> productList = productRepository.findAllById(orderProductDto.productsId());
        if (productList.isEmpty()) {
            throw new RuntimeException("No valid products found for IDs: " + orderProductDto.productsId());
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        Order order = new Order();
        order.setUser(user);
        order.setProducts(productList);
        orderRepository.save(order);
    }
}
