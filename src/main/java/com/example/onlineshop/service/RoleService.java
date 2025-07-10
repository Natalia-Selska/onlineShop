package com.example.onlineshop.service;

import com.example.onlineshop.entity.enumeration.RoleEnum;
import com.example.onlineshop.entity.model.Role;
import com.example.onlineshop.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class RoleService {
    private final RoleRepository roleRepository;

    @PostConstruct
    public void initRole() {
        List<Role> list = Arrays.stream(RoleEnum.values())
                .filter((roleEnum) -> !roleRepository.existsByName(roleEnum.name()))
                .map(roleEnum -> new Role(roleEnum.name()))
                .toList();
        roleRepository.saveAll(list);

    }

    public void addRole(Role role) {
        log.debug("Сheck if it exists role by id {}", role.getId());
        if (roleRepository.findById(role.getId()).isPresent()) {
            log.error("RoleEnum is already exist {}", role.getId());
            throw new RuntimeException("RoleEnum is already exist");
        } else {
            log.debug("RoleEnum saved in db {}", role.getId());
            roleRepository.save(role);
        }
    }

    public void deleteRole(UUID id) {
        log.debug("Find role by id {}", id);
        if (roleRepository.findById(id).isPresent()) {
            log.debug("RoleEnum deleted bu id {}", id);
            roleRepository.deleteById(id);
        } else {
            log.error("RoleEnum not founded by id {}", id);
            throw new RuntimeException("RoleEnum not founded by id");
        }
    }

    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }

    public Set<Role> getRoles(Set<RoleEnum> roles) {
        Set<String> strings = roles.stream()
                .map((element) -> element.name())
                .collect(Collectors.toSet());
        return roleRepository.findByNameIn(strings);
    }

    public Set<Role> findRoleByEmail(String name) {
        return new HashSet<>(roleRepository.findRoleByEmail(name));
    }


}