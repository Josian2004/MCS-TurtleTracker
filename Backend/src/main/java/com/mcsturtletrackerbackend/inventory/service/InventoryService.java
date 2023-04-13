package com.mcsturtletrackerbackend.inventory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.inventory.event.InventoryPublisher;
import com.mcsturtletrackerbackend.inventory.event.NewInventoryEvent;
import com.mcsturtletrackerbackend.inventory.event.RequestInventoryEvent;
import com.mcsturtletrackerbackend.inventory.model.Inventory;
import com.mcsturtletrackerbackend.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final InventoryRepository inventoryRepository;
    private final InventoryPublisher inventoryPublisher;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, InventoryPublisher inventoryPublisher) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryPublisher = inventoryPublisher;
    }

    public String convertToJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void requestInventory(Long computerId) {
        inventoryPublisher.PublishRequestInventoryEvent(new RequestInventoryEvent(computerId));
    }

    public void newInventory(Inventory inventory){
        inventoryPublisher.PublishNewInventoryEvent(new NewInventoryEvent(convertToJson(inventory)));
        addNewInventory(inventory);
    }


    private void addNewInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }
}
