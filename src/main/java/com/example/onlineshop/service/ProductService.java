package com.example.onlineshop.service;

import com.example.onlineshop.entity.User;
import com.example.onlineshop.entity.dto.ProductDto;
import com.example.onlineshop.entity.dto.ProductUpdateDto;
import com.example.onlineshop.entity.model.Product;
import com.example.onlineshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final AuthorizationService authorizationService;

    @Transactional
    public void addProduct(ProductDto productDto, String token) {
        UUID id = authorizationService.extractUserId(token);
        log.info("Looking for a user by id, whether he is authorized {}", id);
        Optional<User> user = userService.findById(id);
        String name = productDto.name();
        Integer count = productDto.count();
        BigDecimal cost = productDto.cost();
        log.debug("Find product by name {}", name);
        Product product = productRepository.findByName(name)
                .orElseGet(() -> {
                    Product newProduct = Product.builder()
                            .cost(cost)
                            .count(count)
                            .name(name)
                            .build();
                    log.info("Product build by name {}", name);
                    log.debug("Product save {}", newProduct);
                    return productRepository.save(newProduct);
                });
    }

    public Product updateProduct(ProductUpdateDto productUpdateDto, UUID id) {
        String name = productUpdateDto.name();
        Integer count = productUpdateDto.count();
        BigDecimal cost = productUpdateDto.cost();
        log.debug("Product find by id {}", name);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("This product not find by id {}", id);
                    return new RuntimeException("This product not find");
                });
        log.info("Product update {}", product);
        if (name != null) {
            product.setName(name);
        }
        if (cost != null) {
            product.setCost(cost);
        }
        if (count != null) {
            product.setCount(count);
        }
        log.debug("Product saved after update {}", name);
        productRepository.save(product);
        return product;
    }

    @Transactional
    public void deleteProduct(UUID id) {
        log.debug("Find product by id {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (!product.getOrderList().isEmpty()) {
            log.error("Can not delete: this product user in order {}", product);
            throw new RuntimeException("Can not delete: this product used in order");
        }
        productRepository.delete(product);
        log.debug("Deleted product by id {}", id);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> findAllById(List<UUID> productId) {
        return productRepository.findAllById(productId);
    }

}
