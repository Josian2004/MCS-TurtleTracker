package com.mcsturtletrackerbackend.inventory.event;

public class RequestInventoryEvent {
    public RequestInventoryEvent(long computerId) {
        this.computerId = computerId;
    }

    private long computerId;

    public long getComputerId() {
        return computerId;
    }
}
