package com.example.onlineshop.repository;

import com.example.onlineshop.entity.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT MAX(o.number) FROM Order o")
    Integer findMaxOrderNumber();

    List<Order> findByUserId(UUID id);

    @Query("SELECT COALESCE(MAX(o.number), 0) + 1 FROM Order o")
    Integer getNextOrderNumber();

}
