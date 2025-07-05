package com.example.onlineshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.onlineshop.entity.model.User;
import com.example.onlineshop.entity.dto.UserAuthorizationDto;
import com.example.onlineshop.entity.dto.UserRegistrationDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.onlineshop.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorizationService authorizationService;

    @Transactional
    public void registration(UserRegistrationDto user) {
        String firstName = user.firstName();
        String lastName = user.lastName();
        String password = passwordEncoder.encode(user.password());
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
        log.debug("check if the password is correct");
        if (!passwordEncoder.matches(password, user1.getPassword())) {
            log.error("invalid password");
            throw new RuntimeException("Invalid password");
        } else {
            log.info("issue a token");
            return authorizationService.genetateToken(id, email);
        }
    }

    public User updateInfo(UserRegistrationDto user) {
        String firstName = user.firstName();
        String lastName = user.lastName();
        String password = passwordEncoder.encode(user.password());
        String email = user.email();
        User user1 = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("Not found this email"));

        user1.setEmail(email);
        user1.setPassword(password);
        user1.setFirstName(firstName);
        user1.setLastName(lastName);
        return userRepository.save(user1);
    }

    public User findById(UUID uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() -> {
                    log.error("User not found by id");
                    return new RuntimeException("User not found with id ");
                });
    }
}
