package com.example.onlineshop.service;

import com.example.onlineshop.entity.dto.ProductDto;
import com.example.onlineshop.entity.model.Product;
import com.example.onlineshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public void addProduct(ProductDto productDto) {
        String name = productDto.name();
        Integer count = productDto.count();
        Float cost = productDto.cost();
        log.debug("Find product by name{}", name);
        Product product = productRepository.findByName(name);
        if (product != null) {
            log.error("Product not found by name{}", name);
            throw new RuntimeException("This product already exists");
        }
        log.info("Product build");
        Product product1 = Product.builder()
                .cost(cost)
                .count(count)
                .name(name)
                .build();
        log.debug("Product save");
        productRepository.save(product1);
    }

    public Product updateProduct(ProductDto productDto, UUID id) {
        String name = productDto.name();
        Integer count = productDto.count();
        Float cost = productDto.cost();
        log.debug("Product find by id{}", name);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("This product not find");
                    return new RuntimeException("This product not find");
                });
        log.info("Product update");
        if (name != null) {
            product.setName(name);
        }
        if (cost != null) {
            product.setCost(cost);
        }
        if (count != null) {
            product.setCount(count);
        }

        log.debug("Product saved after update{}", name);
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(String name) {
        log.debug("Find product by name");
        Product product = productRepository.findByName(name);
        if (product != null) {
            productRepository.delete(product);
            log.debug("Deleted product");
        } else {
            log.error("This product not found");
            throw new RuntimeException("This product not found");
        }

    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

}
