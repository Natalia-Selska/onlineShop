package com.example.onlineshop.entity.dto;

import jakarta.persistence.Column;

public record ProductDto(
        String name,
        Integer count,
        Float cost
) {

}
