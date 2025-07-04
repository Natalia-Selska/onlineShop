package com.example.onlineshop.service;

import com.example.onlineshop.entity.dto.OrderProductDto;
import com.example.onlineshop.entity.model.Order;
import com.example.onlineshop.entity.model.Product;
import com.example.onlineshop.entity.model.User;
import com.example.onlineshop.repository.OrderRepository;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public void addOrder(OrderProductDto orderProductDto, UUID id) {
        log.debug("Find all products by id");
        List<Product> productList = productRepository.findAllById(orderProductDto.productsId());
        if (productList.isEmpty()) {
            log.error("Not found products");
            throw new RuntimeException("No valid products found for IDs: " + orderProductDto.productsId());
        }
        log.debug("Find user by id");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        log.error("User not found by id");
        Order order = new Order();
        order.setUser(user);
        order.setProducts(productList);
        log.debug("Find max number order");
        Integer number = orderRepository.findMaxOrderNumber();
        order.setNumber(number == null ? 1 : number + 1);
        orderRepository.save(order);
    }


    public List<Order> findOrderByUser(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found by email"));
        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orders;

    }
}
