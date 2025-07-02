package com.example.onlineshop.entity.dto;

public record UserAuthorizationDto(
        String login,
        String password,
        String email
) {
}
