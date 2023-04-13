package com.mcsturtletrackerbackend.messages.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.LocalDateTime;

@Document(collection = "messages")
public class Message {

    protected Message() {
    }

    public Message(String message, MessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public Message(String message, Object metaData, MessageType messageType) {
        this.message = message;
        this.metaData = metaData;
        this.messageType = messageType;
    }

    @Id
    private String id;

    private String message;
    private Object metaData;

    private MessageType messageType;

    private String creationDateTime =  LocalDateTime.now().toString();

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getCreationDateTime() {
        return creationDateTime;
    }

    public Object getMetaData() {
        return metaData;
    }
}
