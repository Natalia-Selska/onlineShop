package com.example.onlineshop.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthorizationDto(
        @NotBlank
        String password,
        @NotBlank
        @Email
        String email
) {
}
