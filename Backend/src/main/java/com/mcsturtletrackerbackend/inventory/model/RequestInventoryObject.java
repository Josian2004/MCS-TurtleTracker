package com.mcsturtletrackerbackend.inventory.model;

public class RequestInventoryObject {

    public RequestInventoryObject(long computerId) {
        this.computerId = computerId;
    }

    private long computerId;

    public long getComputerId() {
        return computerId;
    }

    public String getMessage() {
        return message;
    }

    private String message = "request inventory";
}
