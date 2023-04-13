package com.mcsturtletrackerbackend.messages.api;

import com.mcsturtletrackerbackend.messages.event.NewMessageEvent;
import com.mcsturtletrackerbackend.messages.service.MessageService;
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
public class AppMessagesWebSocket extends TextWebSocketHandler {
    ArrayList<WebSocketSession> sessions;
    MessageService messageService;
    @Autowired
    public AppMessagesWebSocket(MessageService messageService) {
        this.messageService = messageService;
        this.sessions = new ArrayList<>();
    }

    @EventListener
    public void sendNewLog(NewMessageEvent newMessageEvent){
        String logJson = newMessageEvent.getNewLogJson();
        try {
            for (WebSocketSession session: sessions){
                session.sendMessage(new TextMessage(logJson));
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