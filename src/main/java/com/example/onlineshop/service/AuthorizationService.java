package com.example.onlineshop.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthorizationService {

    private final Key secretKey = Keys.hmacShaKeyFor("super-secret-key-12345678901234567890".getBytes());

    public String genetateToken(UUID id, String email) {
        return Jwts.builder()
                .subject(email) // що записати в токен (користувача)
                .issuedAt(new Date()) // коли створено
                .claim("id", id)
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

}
