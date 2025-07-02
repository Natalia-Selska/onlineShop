package com.example.onlineshop.entity.dto;

public record UserAuthorizationDto(
        String password,
        String email
) {
}
