package com.mcsturtletrackerbackend.computers.event;

public class NewComputerEvent {
    public NewComputerEvent(String  newComputerJson) {
        this.newComputerJson = newComputerJson;
    }

    public String getNewComputerJson() {
        return newComputerJson;
    }

    private String newComputerJson;
}
