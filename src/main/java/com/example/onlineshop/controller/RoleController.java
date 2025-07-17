package com.example.onlineshop.controller;

import com.example.onlineshop.entity.Role;
import com.example.onlineshop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;


}
