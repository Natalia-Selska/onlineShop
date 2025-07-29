package com.example.onlineshop.controller;

import com.example.onlineshop.entity.dto.OrderProductDto;
import com.example.onlineshop.entity.model.Order;

import com.example.onlineshop.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public void addOrder( @RequestHeader("token") String token,@Valid @RequestBody OrderProductDto orderProductDto) {
        orderService.addOrder(orderProductDto, token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public List<Order> findOrderByUser(@PathVariable("id") UUID id) {
        return orderService.findOrderByUser(id);
    }

}
