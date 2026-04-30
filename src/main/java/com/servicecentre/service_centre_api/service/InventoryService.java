package com.servicecentre.service_centre_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.servicecentre.service_centre_api.model.Inventory;
import com.servicecentre.service_centre_api.exception.ResourceNotFoundException;
import com.servicecentre.service_centre_api.exception.InsufficientStockException;
import com.servicecentre.service_centre_api.repository.InventoryRepository;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository; // final added ✅

    public List<Inventory> getAllItems() {
        return inventoryRepository.findAll();
    }

    public Inventory addItem(Inventory item) {
        return inventoryRepository.save(item);
    }

    public Inventory updateItem(Long id, Inventory updateItem) {
        Inventory existingItem = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found with id: " + id));

        existingItem.setItemName(updateItem.getItemName());
        existingItem.setCategory(updateItem.getCategory());
        existingItem.setQuantity(updateItem.getQuantity());
        existingItem.setUnitPrice(updateItem.getUnitPrice());
        existingItem.setLowStockThreshold(updateItem.getLowStockThreshold()); // ✅
        return inventoryRepository.save(existingItem);
    }

    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findAll()
                .stream()
                .filter(Inventory::isLowStock) // entity ka method use kiya ✅
                .collect(Collectors.toList());
    }

    public Inventory deductStock(Long itemId, Integer quantity) {
        Inventory item = inventoryRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Item not found with id: " + itemId));

        if (item.getQuantity() < quantity) {
            throw new InsufficientStockException(
                    "Insufficient stock for item: " + item.getItemName()); // ✅
        }
        item.setQuantity(item.getQuantity() - quantity);
        return inventoryRepository.save(item);
    }
}