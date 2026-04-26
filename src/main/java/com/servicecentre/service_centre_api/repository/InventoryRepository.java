package com.servicecentre.service_centre_api.repository;

import com.servicecentre.service_centre_api.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByQuantityLessThanEqual(Integer threshold);
    List<Inventory> findByCategoryIgnoreCase(String category);
}