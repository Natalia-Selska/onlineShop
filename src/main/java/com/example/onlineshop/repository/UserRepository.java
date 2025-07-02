package com.example.onlineshop.repository;

import jakarta.validation.constraints.Email;
import com.example.onlineshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsUserByEmail( String email);

    Optional<User> findUserByEmail(String email);
}
