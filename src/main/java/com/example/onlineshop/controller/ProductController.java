package com.example.onlineshop.controller;

import com.example.onlineshop.entity.dto.ProductDto;
import com.example.onlineshop.entity.model.Product;
import com.example.onlineshop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;


    @PostMapping()
    public void addProduct(@Valid @RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@Valid @RequestBody ProductDto productDto, @PathVariable("id") UUID id) {
        return productService.updateProduct(productDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") UUID id) {
        productService.deleteProduct(id);
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }
}
