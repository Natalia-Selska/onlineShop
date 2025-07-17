package com.example.onlineshop.service;

import com.example.onlineshop.entity.Role;
import com.example.onlineshop.entity.enumeration.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthorizationService {

    private final Key secretKey = Keys.hmacShaKeyFor("super-secret-key-12345678901234567890".getBytes());

    public String genetateToken(UUID id, String email, Set<Role> role) {
        List<String> roleNames = role.stream()
                .map((role1)->role1.getRoleEnum().name())
                .toList();
        return Jwts.builder()
                .subject(email) // що записати в токен (користувача)
                .issuedAt(new Date()) // коли створено
                .claim("id", id)
                .claim("role", roleNames)
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // коли закінчується (24 години)
                .signWith(secretKey) // підписати токен секретом
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(new SecretKeySpec(secretKey.getEncoded(), SignatureAlgorithm.HS256.getJcaName()))
                    .build()
                    .parse(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    public Set<RoleEnum> extractRoles(String token) {
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        List<String> roleNames = claims.get("role", List.class);

        return roleNames.stream()
                .map(RoleEnum::valueOf)
                .collect(Collectors.toSet());
    }




}
