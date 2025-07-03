package com.example.onlineshop.repository;

import com.example.onlineshop.entity.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findByName(String name);

    void deleteProductByName(String name);
}
