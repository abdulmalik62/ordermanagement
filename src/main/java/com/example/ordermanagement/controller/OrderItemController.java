package com.example.ordermanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.ordermanagement.dto.CreateOrderItemRequest;
import com.example.ordermanagement.dto.UpdateOrderItemRequest;
import com.example.ordermanagement.dto.OrderItemResponse;
import com.example.ordermanagement.service.OrderItemService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService service;

    public OrderItemController(OrderItemService service) {
        this.service = service;
    }

    // CREATE
    @Operation(summary = "Create order item")
    @PostMapping
    public OrderItemResponse create(
            @RequestBody CreateOrderItemRequest request) {
        return service.create(request);
    }

    // GET ACTIVE
    @Operation(summary = "Get active order items")
    @GetMapping
    public List<OrderItemResponse> getActive() {
        return service.getActiveItems();
    }

    // GET DELETED
    @Operation(summary = "Get deleted order items")
    @GetMapping("/deleted")
    public List<OrderItemResponse> getDeleted() {
        return service.getDeletedItems();
    }

    // PATCH UPDATE
    @Operation(summary = "Update order item partially")
    @PatchMapping("/{id}")
    public OrderItemResponse update(
            @PathVariable Long id,
            @RequestBody UpdateOrderItemRequest request) {
        return service.update(id, request);
    }

    // SOFT DELETE
    @Operation(summary = "Soft delete order item")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // RESTORE
    @Operation(summary = "Restore deleted order item")
    @PatchMapping("/{id}/restore")
    public OrderItemResponse restore(@PathVariable Long id) {
        return service.restore(id);
    }
}
