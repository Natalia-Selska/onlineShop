package com.example.onlineshop.entity.dto;

import jakarta.persistence.Column;

import java.math.BigDecimal;

public record ProductDto(
        @Column(nullable = false)
        String name,
        @Column(nullable = false)
        Integer count,
        @Column(nullable = false)
        BigDecimal cost
) {

}
