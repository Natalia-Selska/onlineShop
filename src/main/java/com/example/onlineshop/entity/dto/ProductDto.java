package com.example.onlineshop.entity.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record ProductDto(
        @NotEmpty
        @Pattern(regexp = "^[A-Za-z]+$")
        String name,
        @NotNull
        Integer count,
        @NotNull
        BigDecimal cost
) {

}
