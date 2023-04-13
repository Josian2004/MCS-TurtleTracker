package com.mcsturtletrackerbackend.computers.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.computers.event.NewComputerEvent;
import com.mcsturtletrackerbackend.computers.service.InfoService;
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
public class AppInfoWebSocketTextHandler extends TextWebSocketHandler {
    private ArrayList<WebSocketSession> sessions;
    InfoService infoService;
    ObjectMapper objectMapper;
    @Autowired
    public AppInfoWebSocketTextHandler(InfoService infoService) {
        this.infoService = infoService;
        this.sessions = new ArrayList<>();
        this.objectMapper = new ObjectMapper();
    }

    @EventListener
    public void sendNewComputer(NewComputerEvent newComputerEvent){
        String computerJson = newComputerEvent.getNewComputerJson();
        try {
            for (WebSocketSession session: sessions){
                session.sendMessage(new TextMessage(computerJson));
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