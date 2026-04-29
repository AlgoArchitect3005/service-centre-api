package com.servicecentre.service_centre_api.service;
import java.util.List;

import org.springframework.stereotype.Service;
import com.servicecentre.service_centre_api.model.Inventory;
import com.servicecentre.service_centre_api.exception.ResourceNotFoundException;
import com.servicecentre.service_centre_api.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class InventoryService {
    
    private  InventoryRepository inventoryRepository;

    public List<Inventory> getAllItems(){
        return inventoryRepository.findAll();
    }
    public Inventory addItem(Inventory item){
        return inventoryRepository.save(item);
    }
    public Inventory updateItem(Long id , Inventory updateItem){
        Inventory existingItem = inventoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));

        existingItem.setItemName(updateItem.getItemName());
        existingItem.setCategory(updateItem.getCategory());
        existingItem.setQuantity(updateItem.getQuantity());
        existingItem.setUnitPrice(updateItem.getUnitPrice());
        return inventoryRepository.save(existingItem);
    }

}
