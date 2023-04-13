package com.mcsturtletrackerbackend.locations.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.locations.model.Location;
import com.mcsturtletrackerbackend.locations.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class McsLocationsWebSocketTextHandler extends TextWebSocketHandler {
    ArrayList<WebSocketSession> sessions;
    LocationService locationService;
    @Autowired
    public McsLocationsWebSocketTextHandler(LocationService locationService) {
        this.locationService = locationService;
        this.sessions = new ArrayList<>();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws InterruptedException, IOException {
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Location location = objectMapper.readValue(payload, Location.class);
            locationService.newLocation(location);
            session.sendMessage(new TextMessage(HttpStatus.OK.value() + " - ok"));
        }
        catch (JsonParseException e){
            System.out.println(LocalDateTime.now() + " Invalid JSON in location message");
            session.sendMessage(new TextMessage(HttpStatus.BAD_REQUEST.value() + " - Sent JSON was not of the right format"));
        }

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println(LocalDateTime.now() + "[Locations] New Connection established");
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        sessions.remove(session);
        System.out.println(LocalDateTime.now() + "[Locations] Connection lost");
    }


}