package com.example.onlineshop.repository;

import com.example.onlineshop.entity.model.Role;
import com.example.onlineshop.entity.enumeration.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findRoleByRoleEnum(RoleEnum roleEnum);

}
