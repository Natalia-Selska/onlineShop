package com.example.onlineshop.controller;

import lombok.RequiredArgsConstructor;
import com.example.onlineshop.entity.dto.UserAuthorizationDto;
import com.example.onlineshop.entity.dto.UserRegistrationDto;
import org.springframework.web.bind.annotation.*;
import com.example.onlineshop.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public void registration(@RequestBody UserRegistrationDto user) {
        userService.registration(user);
    }

    @PostMapping("/authorization")
    public String authorization(@RequestBody UserAuthorizationDto user) {
     return   userService.authorization(user);
    }
}
