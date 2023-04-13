package com.mcsturtletrackerbackend.computers.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.ResponseObject;
import com.mcsturtletrackerbackend.computers.model.Computer;
import com.mcsturtletrackerbackend.computers.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class McsInfoWebSocketTextHandler extends TextWebSocketHandler {
    ArrayList<WebSocketSession> sessions;
    InfoService infoService;
    @Autowired
    public McsInfoWebSocketTextHandler(InfoService infoService) {
        this.infoService = infoService;
        this.sessions = new ArrayList<>();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Computer computer = objectMapper.readValue(payload, Computer.class);
            infoService.newComputer(computer);
            session.sendMessage(new TextMessage(HttpStatus.OK.value() + " - ok"));
        }
        catch (JsonParseException e){
            System.out.println(LocalDateTime.now() + " Invalid JSON in info message");
            session.sendMessage(new TextMessage(HttpStatus.BAD_REQUEST.value() + " - Sent JSON was not of the right format"));
        }

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println(LocalDateTime.now() + "[Info] New Connection established");
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        sessions.remove(session);
        System.out.println(LocalDateTime.now() + "[Info] Connection lost");
    }


}