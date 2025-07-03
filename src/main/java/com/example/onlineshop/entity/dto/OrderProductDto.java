package com.example.onlineshop.entity.dto;

import com.example.onlineshop.entity.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record OrderProductDto(
        List<UUID> productsId
) {
}
