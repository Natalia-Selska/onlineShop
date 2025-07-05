package com.example.onlineshop.service;

import com.example.onlineshop.entity.dto.ProductDto;
import com.example.onlineshop.entity.model.Product;
import com.example.onlineshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        BigDecimal cost = productDto.cost();
        log.debug("Find product by name{}", name);
        Product product = productRepository.findByName(name)
                .orElseGet(() -> {
                    log.info("Product build");
                    Product product1 = Product.builder()
                            .cost(cost)
                            .count(count)
                            .name(name)
                            .build();
                    log.debug("Product save");
                    return productRepository.save(product1);
                });
    }

    public Product updateProduct(ProductDto productDto, UUID id) {
        String name = productDto.name();
        Integer count = productDto.count();
        BigDecimal cost = productDto.cost();
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
        productRepository.save(product);
        return product;
    }

    @Transactional
    public void deleteProduct(UUID id) {
        log.debug("Find product by name");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found")
                );
        if (!product.getOrderList().isEmpty()) {
            log.error("Can not delete: this product user in order{}", product);
            throw new RuntimeException("Can not delete: this product used in order");
        }
        productRepository.delete(product);
        log.debug("Deleted product");
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> findAllById(List<UUID> productId) {
        return productRepository.findAllById(productId);
    }

}
