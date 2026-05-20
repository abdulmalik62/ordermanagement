package com.example.ordermanagement.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.ordermanagement.dto.CreateOrderItemRequest;
import com.example.ordermanagement.dto.UpdateOrderItemRequest;
import com.example.ordermanagement.dto.OrderItemResponse;
import com.example.ordermanagement.model.OrderItem;
import com.example.ordermanagement.repository.OrderItemRepository;

@Service
public class OrderItemService {

    private final OrderItemRepository repository;

    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public OrderItemResponse create(CreateOrderItemRequest request) {

        OrderItem item = new OrderItem(
                request.getProductName(),
                request.getQuantity(),
                request.getPrice()
        );

        return mapToResponse(repository.save(item));
    }

    // READ (only active)
    public List<OrderItemResponse> getActiveItems() {
        return repository.findByIsDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // READ deleted
    public List<OrderItemResponse> getDeletedItems() {
        return repository.findByIsDeletedTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // PATCH update
    public OrderItemResponse update(Long id, UpdateOrderItemRequest request) {

        OrderItem existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));

        if (request.getProductName() != null)
            existing.setProductName(request.getProductName());

        if (request.getQuantity() != null)
            existing.setQuantity(request.getQuantity());

        if (request.getPrice() != null)
            existing.setPrice(request.getPrice());

        return mapToResponse(repository.save(existing));
    }

    // SOFT DELETE
    public void delete(Long id) {
        OrderItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));

        item.softDelete();
        repository.save(item);
    }

    // RESTORE
    public OrderItemResponse restore(Long id) {
        OrderItem item = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));

        item.restore();
        return mapToResponse(repository.save(item));
    }

    // =====================
    // MAPPING METHOD
    // =====================
    private OrderItemResponse mapToResponse(OrderItem item) {

        OrderItemResponse response = new OrderItemResponse();
        response.setId(item.getId());
        response.setProductName(item.getProductName());
        response.setQuantity(item.getQuantity());
        response.setPrice(item.getPrice());
        response.setCreatedAt(item.getCreatedAt());
        response.setUpdatedAt(item.getUpdatedAt());

        return response;
    }
}
