package com.example.onlineshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.onlineshop.entity.dto.UserAuthorizationDto;
import com.example.onlineshop.entity.dto.UserRegistrationDto;
import org.springframework.web.bind.annotation.*;
import com.example.onlineshop.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void addUserAndRole() {
        roleService.createIfNotExist(RoleEnum.ADMIN_ROLE);
        roleService.createIfNotExist(RoleEnum.USER_ROLE);
        Role adminRole = roleService.findRoleByRoleEnum(ADMIN_ROLE);
        Role userRole = roleService.findRoleByRoleEnum(USER_ROLE);

        User user = User.builder()
                .email("admin@gmail.com")
                .firstName("Admin")
                .lastName("Admin")
                .password(passwordEncoder.encode("qwertyui"))
                .roles(Set.of(adminRole, userRole))
                .build();
        Optional<User> user1 = userService.findUserByEmail(user.getEmail());
        if (user1.isEmpty()) {
            userService.save(user);
        }
    }

    @PostMapping("/registration")
    public void registration(@RequestBody @Valid UserRegistrationDto user) {
        userService.registration(user);
    }

    @PostMapping("/authorization")
    public String authorization(@RequestBody @Valid UserAuthorizationDto user) {
     return   userService.authorization(user);
    }
}
