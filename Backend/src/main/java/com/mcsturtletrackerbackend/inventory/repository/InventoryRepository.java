package com.mcsturtletrackerbackend.inventory.repository;

import com.mcsturtletrackerbackend.inventory.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    void deleteByComputerId(long id);
    boolean existsByComputerId(long id);
}
