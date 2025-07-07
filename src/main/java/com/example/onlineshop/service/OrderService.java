package com.example.onlineshop.service;

import com.example.onlineshop.entity.dto.OrderProductDto;
import com.example.onlineshop.entity.model.Order;
import com.example.onlineshop.entity.model.Product;
import com.example.onlineshop.entity.model.User;
import com.example.onlineshop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final ProductService productService;


    @Transactional
    public void addOrder(OrderProductDto orderProductDto, String token) {
        log.debug("Find all products by id {}", orderProductDto.productsId());
        List<Product> productList = productService.findAllById(orderProductDto.productsId());
        if (productList.isEmpty()) {
            log.error("Not found products by id");
            throw new RuntimeException("No valid products found for IDs: " + orderProductDto.productsId());
        }
        UUID id = authorizationService.extractUserId(token);
        log.debug("Find user by id {}", id);
        User user = userService.findById(id);
        Order order = new Order();
        order.setUser(user);
        order.setProducts(productList);
        order.setNumber(orderRepository.getNextOrderNumber());
        orderRepository.save(order);
    }

    public List<Order> findOrderByUser(UUID id) {
        log.info("Find user by id {}", id);
        User user = userService.findById(id);
        log.info("Find all orders by user id {}", id);
        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orders;

    }
}
