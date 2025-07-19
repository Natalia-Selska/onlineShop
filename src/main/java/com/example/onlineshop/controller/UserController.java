package com.example.onlineshop.controller;

import com.example.onlineshop.entity.model.Role;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.service.RoleService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.onlineshop.entity.dto.UserAuthorizationDto;
import com.example.onlineshop.entity.dto.UserRegistrationDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.onlineshop.service.UserService;

import java.util.Optional;
import java.util.Set;

import static com.example.onlineshop.entity.enumeration.RoleEnum.ADMIN_ROLE;
import static com.example.onlineshop.entity.enumeration.RoleEnum.USER_ROLE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public void registration(@RequestBody @Valid UserRegistrationDto user) {
        userService.registration(user);
    }

    @PostMapping("/authorization")
    public String authorization(@RequestBody @Valid UserAuthorizationDto user) {
        return userService.authorization(user);
    }
}
