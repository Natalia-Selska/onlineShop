package com.example.onlineshop.entity.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UserRegistrationDto
        (@NotBlank
         String firstName,
         @NotBlank
         String lastName,
         @Email
         @NotBlank
         String email,
         @NotBlank
         String password) {
}
