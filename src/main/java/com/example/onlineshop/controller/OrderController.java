package com.example.onlineshop.controller;

import com.example.onlineshop.entity.dto.OrderProductDto;
import com.example.onlineshop.entity.model.Product;
import com.example.onlineshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/addOrder/{userId}")
    public void addOrder(@PathVariable("userId") UUID userId, @RequestBody OrderProductDto orderProductDto) {
        orderService.addOrder(orderProductDto, userId);
    }
}
