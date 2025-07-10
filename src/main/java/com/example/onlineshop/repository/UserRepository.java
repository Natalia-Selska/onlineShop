package com.example.onlineshop.repository;

import com.example.onlineshop.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsUserByEmail(String email);

    Optional<User> findUserByEmail(String email);

    Optional<User> findByEmail(String email);
}
