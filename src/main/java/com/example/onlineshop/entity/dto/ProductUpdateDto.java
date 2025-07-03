package com.example.onlineshop.entity.dto;

import java.util.UUID;

public record ProductUpdateDto(
        String name,
        Integer count,
        Float cost

) {

}
