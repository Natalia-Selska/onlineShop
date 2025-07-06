package com.example.onlineshop.entity.dto;

import jakarta.validation.constraints.Pattern;

import java.beans.BeanInfo;
import java.math.BigDecimal;
import java.util.UUID;

public record ProductUpdateDto(
        @Pattern(regexp = "^[A-Za-z]+$")
        String name,
        Integer count,
        BigDecimal cost

) {

}
