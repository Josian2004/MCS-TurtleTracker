package com.mcsturtletrackerbackend.computers.config;

import com.mcsturtletrackerbackend.computers.api.AppInfoWebSocketTextHandler;
import com.mcsturtletrackerbackend.computers.api.McsInfoWebSocketTextHandler;
import com.mcsturtletrackerbackend.main;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class InfoWebSocketConfig implements WebSocketConfigurer {

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        AppInfoWebSocketTextHandler appInfoWebSocketTextHandler = main.getBean(AppInfoWebSocketTextHandler.class);
        McsInfoWebSocketTextHandler mcsInfoWebSocketTextHandler = main.getBean(McsInfoWebSocketTextHandler.class);

        registry.addHandler(mcsInfoWebSocketTextHandler, "/ws/mcs/info").setAllowedOrigins("*");
        registry.addHandler(appInfoWebSocketTextHandler, "/ws/app/info").setAllowedOrigins("*");

    }


}
