package com.example.onlineshop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.entity.dto.UserAuthorizationDto;
import com.example.onlineshop.entity.dto.UserRegistrationDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.onlineshop.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorizationService authorizationService;

    public void registration(UserRegistrationDto user) {
        String firstName = user.firstName();
        String lastName = user.lastName();
        String login = user.login();
        String password = passwordEncoder.encode(user.password());
        String email = user.email();
        int age = user.age();
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
                .login(login)
                .age(age)
                .build();
        log.debug("User save in repository");
        userRepository.save(user1);
    }

    public String authorization(UserAuthorizationDto user) {
        String login = user.login();
        String password = user.password();
        String email = user.email();
        log.debug("Find user by login");
        User user1 = userRepository.findUserByLogin(login)
                .orElseThrow(() -> {
                    log.error("User not found by login");
                    return new RuntimeException("User not found by Login");
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

}
