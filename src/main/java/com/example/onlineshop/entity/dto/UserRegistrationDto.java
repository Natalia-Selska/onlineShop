package com.example.onlineshop.entity.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;

import java.util.UUID;

public record UserRegistrationDto
        (String firstName,
         String lastName,
         int age,
         String email,
         String password,
         String login) {
}
