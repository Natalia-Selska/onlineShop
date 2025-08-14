package com.example.onlineshop.controller;

import com.example.onlineshop.entity.dto.ProductDto;
import com.example.onlineshop.entity.dto.ProductUpdateDto;
import com.example.onlineshop.entity.model.Product;
import com.example.onlineshop.service.AuthorizationService;
import com.example.onlineshop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping
    public void addProduct(
            @Valid @RequestBody ProductDto productDto,
            @RequestHeader("Authorization") String token) {
        productService.addProduct(productDto, token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public Product updateProduct(@Valid @RequestBody ProductUpdateDto productUpdateDto, @PathVariable("id") UUID id) {
        return productService.updateProduct(productUpdateDto, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") UUID id) {
        productService.deleteProduct(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }
}
