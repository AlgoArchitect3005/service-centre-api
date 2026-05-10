package com.servicecentre.service_centre_api.controller;

import com.servicecentre.service_centre_api.model.Inventory;
import com.servicecentre.service_centre_api.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllItems() {
        return ResponseEntity.ok(inventoryService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getItemById(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getItemById(id));
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Inventory>> getLowStockItems() {
        return ResponseEntity.ok(inventoryService.getLowStockItems());
    }

    @PostMapping
    public ResponseEntity<Inventory> addItem(
            @RequestBody Inventory item) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryService.addItem(item));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Inventory> updateItem(
            @PathVariable Long id,
            @RequestBody Inventory item) {
        return ResponseEntity.ok(inventoryService.updateItem(id, item));
    }
}