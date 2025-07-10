package com.example.onlineshop.controller;

import com.example.onlineshop.entity.model.Role;
import com.example.onlineshop.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public void addRole(@RequestBody Role role) {
        roleService.addRole(role);
    }

    @DeleteMapping
    public void deleteRole(@PathVariable("id") UUID id) {
        roleService.deleteRole(id);
    }
}
