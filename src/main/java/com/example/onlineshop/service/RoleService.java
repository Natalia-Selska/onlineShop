package com.example.onlineshop.service;

import com.example.onlineshop.entity.model.Role;
import com.example.onlineshop.entity.enumeration.RoleEnum;
import com.example.onlineshop.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findRoleByRoleEnum(RoleEnum roleEnum) {
        return roleRepository.findRoleByRoleEnum(roleEnum)
                .orElseThrow();
    }

    public void createIfNotExist(RoleEnum roleEnum) {
        if (!roleRepository.existsRoleByRoleEnum(roleEnum)) {
            Role role = new Role(roleEnum);
            roleRepository.save(role);
        }
    }

}
