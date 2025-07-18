package com.example.onlineshop.controller;

import com.example.onlineshop.entity.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.onlineshop.entity.dto.UserAuthorizationDto;
import com.example.onlineshop.entity.dto.UserRegistrationDto;
import org.springframework.web.bind.annotation.*;
import com.example.onlineshop.service.UserService;

import java.util.UUID;

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

    @PutMapping
    public User updateInfo(@RequestBody UserRegistrationDto userRegistrationDto) {
        return userService.updateInfo(userRegistrationDto);
    }


}
