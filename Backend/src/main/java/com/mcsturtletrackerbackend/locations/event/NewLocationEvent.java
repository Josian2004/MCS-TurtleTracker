package com.mcsturtletrackerbackend.locations.event;

public class NewLocationEvent {
    public NewLocationEvent(String  newLocationJson) {
        this.newLocationJson = newLocationJson;
    }

    public String getNewLocationJson() {
        return newLocationJson;
    }

    private String newLocationJson;
}
