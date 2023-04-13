package com.mcsturtletrackerbackend.inventory.api;

import com.mcsturtletrackerbackend.ResponseObject;
import com.mcsturtletrackerbackend.inventory.model.Inventory;
import com.mcsturtletrackerbackend.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("app/inventory")
public class AppInventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public AppInventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseObject getInventory(@RequestParam(value = "computerId")Long computerId) {
        inventoryService.requestInventory(computerId);
        return new ResponseObject(HttpStatus.OK.value(), "Request sent");
    }
}
