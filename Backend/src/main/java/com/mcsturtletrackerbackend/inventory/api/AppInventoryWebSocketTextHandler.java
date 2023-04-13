package com.mcsturtletrackerbackend.inventory.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.inventory.event.NewInventoryEvent;
import com.mcsturtletrackerbackend.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class AppInventoryWebSocketTextHandler extends TextWebSocketHandler {
    private ArrayList<WebSocketSession> sessions;
    InventoryService inventoryService;
    ObjectMapper objectMapper;
    @Autowired
    public AppInventoryWebSocketTextHandler(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.sessions = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
    }

    @EventListener
    public void sendNewInventory(NewInventoryEvent newInventoryEvent){
        String inventoryJson = newInventoryEvent.getNewInventoryJson();
        try {
            for (WebSocketSession session: sessions){
                session.sendMessage(new TextMessage(inventoryJson));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        sessions.remove(session);
    }
}