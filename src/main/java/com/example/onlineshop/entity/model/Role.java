package com.example.onlineshop.entity.model;

import com.example.onlineshop.entity.User;
import com.example.onlineshop.entity.enumeration.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    private RoleEnum roleEnum;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role(RoleEnum roleEnum) {
        this.roleEnum=roleEnum;
    }


}
