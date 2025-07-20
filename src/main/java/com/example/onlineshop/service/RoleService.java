package com.example.onlineshop.service;

import com.example.onlineshop.entity.model.Role;
import com.example.onlineshop.entity.enumeration.RoleEnum;
import com.example.onlineshop.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role findRoleByRoleEnum(RoleEnum roleEnum) {
        return roleRepository.findRoleByRoleEnum(roleEnum)
                .orElseThrow();
    }

    public Set<Role> createRoles(Set<RoleEnum> roleEnums) {
        Set<Role> roles = new HashSet<>();
        for (RoleEnum roleEnum : roleEnums) {
            Role role = roleRepository.findRoleByRoleEnum(roleEnum)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setRoleEnum(roleEnum);
                        return roleRepository.save(newRole);
                    });

            roles.add(role);
        }
        return roles;
    }

}
