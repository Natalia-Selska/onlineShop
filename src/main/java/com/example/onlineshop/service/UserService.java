package com.example.onlineshop.service;

import com.example.onlineshop.entity.model.Role;
import com.example.onlineshop.entity.enumeration.RoleEnum;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AuthorizationService authorizationService;

    @Transactional
    public void registration(UserRegistrationDto user) {
        String firstName = user.firstName();
        String lastName = user.lastName();
        String password = passwordEncoder.encode(user.password());
        Role role = roleService.findRoleByRoleEnum(RoleEnum.USER_ROLE);
        String email = user.email();
        log.debug("We check whether the user exists on email");
        if (userRepository.existsUserByEmail(email)) {
            log.error("User exist with this email");
            throw new RuntimeException("User exist with this email");
        }
        User user1 = User.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .password(password)
                .roles(Set.of(role))
                .build();
        log.debug("User save in repository");
        userRepository.save(user1);
    }


    public String authorization(UserAuthorizationDto user) {
        String password = user.password();
        String email = user.email();
        log.debug("Find user by email{}", email);
        User user1 = userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found by login{}", email);
                    return new RuntimeException("User not found by email");
                });
        UUID id = user1.getId();
        Set<Role> roles = user1.getRoles();
        log.debug("check if the password is correct");
        if (!passwordEncoder.matches(password, user1.getPassword())) {
            log.error("invalid password");
            throw new RuntimeException("Invalid password");
        } else {
            log.info("issue a token");
            return authorizationService.genetateToken(id, email, roles);
        }
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
    public Optional<User> findById(UUID id ){
       return userRepository.findById(id);
    }

}
