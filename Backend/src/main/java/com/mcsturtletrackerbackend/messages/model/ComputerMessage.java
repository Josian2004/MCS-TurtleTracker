package com.mcsturtletrackerbackend.messages.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
@TypeAlias("ComputerMessage")
public class ComputerMessage extends Message{

    protected ComputerMessage() {
    }

    public ComputerMessage(String message, MessageType messageType, int computerId) {
        super(message, messageType);
        this.computerId = computerId;
    }

    public ComputerMessage(String message, Object metaData, MessageType messageType, int computerId) {
        super(message, metaData, messageType);
        this.computerId = computerId;
    }

    private int computerId;

    public int getComputerId() {
        return computerId;
    }
}
