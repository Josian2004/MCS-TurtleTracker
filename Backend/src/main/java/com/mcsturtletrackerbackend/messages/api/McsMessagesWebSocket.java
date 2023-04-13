package com.mcsturtletrackerbackend.messages.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mcsturtletrackerbackend.messages.exceptions.InvalidMessageException;
import com.mcsturtletrackerbackend.messages.model.ComputerMessage;
import com.mcsturtletrackerbackend.messages.model.ServiceMessage;
import com.mcsturtletrackerbackend.messages.service.MessageService;
import org.json.JSONException;
import org.json.JSONObject;
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
public class McsMessagesWebSocket extends TextWebSocketHandler {
    ArrayList<WebSocketSession> sessions;
    MessageService messageService;
    @Autowired
    public McsMessagesWebSocket(MessageService messageService) {
        this.messageService = messageService;
        this.sessions = new ArrayList<>();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JSONObject jsonObject = new JSONObject(payload);
            if (jsonObject.has("computerId")) {
                ComputerMessage computerMessage = objectMapper.readValue(payload, ComputerMessage.class);
                messageService.newMessage(computerMessage);
            } else if (jsonObject.has("serviceName")) {
                ServiceMessage serviceMessage = objectMapper.readValue(payload, ServiceMessage.class);
                messageService.newMessage(serviceMessage);
            } else {
                throw new InvalidMessageException();
            }
            session.sendMessage(new TextMessage(HttpStatus.OK.value() + " - ok"));
        }
        catch (JsonParseException | JSONException e){
            System.out.println(LocalDateTime.now() + " Invalid JSON in info message");
            session.sendMessage(new TextMessage(HttpStatus.BAD_REQUEST.value() + " - Sent JSON was not of the right format"));
        }
        catch (InvalidMessageException e) {
            System.out.println(LocalDateTime.now() + " message was not of type computer or service");
            session.sendMessage(new TextMessage(HttpStatus.BAD_REQUEST.value() + " - JSON was not sent from a computer or service"));
        }
        catch (InvalidFormatException e) {
            System.out.println(LocalDateTime.now() + e.getMessage());
            session.sendMessage(new TextMessage(HttpStatus.BAD_REQUEST.value() + " - " + e.getMessage()));
        }

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println(LocalDateTime.now() + " [Logs] New Connection established");
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        sessions.remove(session);
        System.out.println(LocalDateTime.now() + " [Logs] Connection lost");
    }


}