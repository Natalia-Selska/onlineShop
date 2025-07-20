package com.example.onlineshop.service;

import com.example.onlineshop.entity.model.Role;
import com.example.onlineshop.entity.enumeration.RoleEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.entity.dto.UserAuthorizationDto;
import com.example.onlineshop.entity.dto.UserRegistrationDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.onlineshop.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.example.onlineshop.entity.enumeration.RoleEnum.ADMIN_ROLE;
import static com.example.onlineshop.entity.enumeration.RoleEnum.USER_ROLE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AuthorizationService authorizationService;

    @PostConstruct
    public void addUserAndRole() {
        Set<Role> rolesSet = roleService.createRoles(Set.of(ADMIN_ROLE, USER_ROLE));
        User user = User.builder()
                .email("admin@gmail.com")
                .firstName("Admin")
                .lastName("Admin")
                .password(passwordEncoder.encode("qwertyui"))
                .roles(rolesSet)
                .build();
        userRepository.findUserByEmail(user.getEmail())
                .orElseGet(() -> userRepository.save(user));

    }

    @Transactional
    public void registration(UserRegistrationDto userRegistrationDto) {
        String firstName = userRegistrationDto.firstName();
        String lastName = userRegistrationDto.lastName();
        String password = passwordEncoder.encode(userRegistrationDto.password());
        Role role = roleService.findRoleByRoleEnum(RoleEnum.USER_ROLE);
        String email = userRegistrationDto.email();
        log.debug("We check whether the userRegistrationDto exists by email {}", email);
        if (userRepository.existsUserByEmail(email)) {
            log.error("User exist with this email {}", email);
            throw new RuntimeException("User exist with this email");
        }
        User user = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .roles(Set.of(role))
                .build();
        log.debug("User save in repository by email {}", user.getEmail());
        userRepository.save(user);
    }


    public String authorization(UserAuthorizationDto userAuthorizationDto) {
        String password = userAuthorizationDto.password();
        String email = userAuthorizationDto.email();
        log.debug("Find user by email {}", email);
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found by email {}", email);
                    return new RuntimeException("User not found by email");
                });
        UUID id = user.getId();
        log.debug("check if the password is correct for user with email {}", email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.error("invalid password for user with email {}", email);
            throw new RuntimeException("Invalid password");
        } else {
            Set<Role> roles = user.getRoles();
            log.info("issue a token for user with email {}", email);
            return authorizationService.genetateToken(id, email, roles);
        }
    }

    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

}
