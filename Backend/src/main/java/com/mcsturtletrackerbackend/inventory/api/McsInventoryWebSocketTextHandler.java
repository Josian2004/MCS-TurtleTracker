package com.mcsturtletrackerbackend.inventory.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.inventory.event.RequestInventoryEvent;
import com.mcsturtletrackerbackend.inventory.model.Inventory;
import com.mcsturtletrackerbackend.inventory.model.RequestInventoryObject;
import com.mcsturtletrackerbackend.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class McsInventoryWebSocketTextHandler extends TextWebSocketHandler {
    ArrayList<WebSocketSession> sessions;
    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    public McsInventoryWebSocketTextHandler(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.sessions = new ArrayList<>();
    }

    public String convertToJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            inventoryService.newInventory(objectMapper.readValue(payload, Inventory.class));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @EventListener
    public boolean requestInventory(RequestInventoryEvent requestInventoryEvent) {
        int i = 0;
        for (WebSocketSession session:sessions) {
            try {
                session.sendMessage(new TextMessage(convertToJson(new RequestInventoryObject(requestInventoryEvent.getComputerId()))));
                i++;
            } catch (IOException e) {

            }
        }
        if (i == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println(LocalDateTime.now() + "[Inventory] New Connection established");
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        sessions.remove(session);
        System.out.println(LocalDateTime.now() + "[Inventory] Connection lost");
    }


}