package com.mcsturtletrackerbackend.inventory.event;

public class NewInventoryEvent {
    public NewInventoryEvent(String  newInventoryJson) {
        this.newInventoryJson = newInventoryJson;
    }

    public String getNewInventoryJson() {
        return newInventoryJson;
    }

    private String newInventoryJson;
}
