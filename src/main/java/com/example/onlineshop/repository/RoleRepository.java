package com.example.onlineshop.repository;

import com.example.onlineshop.entity.enumeration.RoleEnum;
import com.example.onlineshop.entity.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    Set<Role> findByNameIn(Set<String> names);

    Role getRoleByName(String name);

    @Query("select u.roles from User u where u.email = :email")
    List<Role> findRoleByEmail(@Param("email") String email);

}
