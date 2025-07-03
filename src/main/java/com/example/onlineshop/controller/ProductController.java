package com.example.onlineshop.controller;

import com.example.onlineshop.entity.dto.ProductDto;
import com.example.onlineshop.entity.model.Product;
import com.example.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;


    @PostMapping("/addProduct")
    public void addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
    }

    @PatchMapping("/updateProduct/{id}")
    public Product updateProduct(@RequestBody ProductDto productDto, @PathVariable("id") UUID id) {
        return productService.updateProduct(productDto, id);
    }

    @DeleteMapping("/deleteProduct/{name}")
    public void deleteProduct(@PathVariable("name") String name) {
        productService.deleteProduct(name);
    }

    @GetMapping
    public List<Product> getProduct() {
        return productService.getProducts();
    }
}
