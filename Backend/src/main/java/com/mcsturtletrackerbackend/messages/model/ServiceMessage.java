package com.mcsturtletrackerbackend.messages.model;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
@TypeAlias("ServiceMessage")
public class ServiceMessage extends Message{

    protected ServiceMessage() {
    }

    public ServiceMessage(String message, MessageType messageType, String serviceName) {
        super(message, messageType);
        this.serviceName = serviceName;
    }

    public ServiceMessage(String message, Object metaData, MessageType messageType, String serviceName) {
        super(message, metaData, messageType);
        this.serviceName = serviceName;
    }

    private String serviceName;

    public String getServiceName() {
        return serviceName;
    }
}
