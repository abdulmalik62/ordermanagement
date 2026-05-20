package com.example.ordermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ordermanagement.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByIsDeletedFalse();

    List<OrderItem> findByIsDeletedTrue();

    
}
