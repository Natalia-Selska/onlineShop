package com.example.onlineshop.entity.dto;

import com.example.onlineshop.entity.model.Product;
import jakarta.persistence.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record OrderProductDto(
        @Column(nullable = false)
        List<UUID> productsId
) {
}
