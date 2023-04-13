package com.mcsturtletrackerbackend.inventory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "inventories")
@TypeAlias(value = "Inventory")
public class Inventory {
    protected Inventory() {
    }

    @Id
    @JsonProperty("computer_id")
    private long computerId;

    private List<Item> items;


    public long getComputerId() {
        return computerId;
    }

    public List<Item> getItems() {
        return items;
    }
}
