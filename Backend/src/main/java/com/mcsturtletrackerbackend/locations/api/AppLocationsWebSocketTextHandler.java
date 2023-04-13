package com.mcsturtletrackerbackend.locations.api;

import com.mcsturtletrackerbackend.locations.event.NewLocationEvent;
import com.mcsturtletrackerbackend.locations.service.LocationService;
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
public class AppLocationsWebSocketTextHandler extends TextWebSocketHandler {
    ArrayList<WebSocketSession> sessions;
    LocationService locationService;
    @Autowired
    public AppLocationsWebSocketTextHandler(LocationService locationService) {
        this.locationService = locationService;
        this.sessions = new ArrayList<>();
    }

    @EventListener
    public void sendNewLocation(NewLocationEvent newLocationEvent){
        String locationJson = newLocationEvent.getNewLocationJson();
        try {
            for (WebSocketSession session: sessions){
                session.sendMessage(new TextMessage(locationJson));
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