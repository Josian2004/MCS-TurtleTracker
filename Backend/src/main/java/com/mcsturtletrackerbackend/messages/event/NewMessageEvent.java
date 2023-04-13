package com.mcsturtletrackerbackend.messages.event;

public class NewMessageEvent {
    public NewMessageEvent(String  newLogJson) {
        this.newLogJson = newLogJson;
    }

    public String getNewLogJson() {
        return newLogJson;
    }

    private String newLogJson;
}
