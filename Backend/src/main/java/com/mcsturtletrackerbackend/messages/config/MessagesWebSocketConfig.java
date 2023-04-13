package com.mcsturtletrackerbackend.messages.config;

import com.mcsturtletrackerbackend.messages.api.AppMessagesWebSocket;
import com.mcsturtletrackerbackend.messages.api.McsMessagesWebSocket;
import com.mcsturtletrackerbackend.main;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class MessagesWebSocketConfig implements WebSocketConfigurer {
    private McsMessagesWebSocket mcsMessagesWebSocket = main.getBean(McsMessagesWebSocket.class);
    private AppMessagesWebSocket appMessagesWebSocket = main.getBean(AppMessagesWebSocket.class);

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(mcsMessagesWebSocket, "/ws/mcs/messages").setAllowedOrigins("*");
        registry.addHandler(appMessagesWebSocket, "/ws/app/messages").setAllowedOrigins("*");;
    }


}
