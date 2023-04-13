package com.mcsturtletrackerbackend.inventory.config;

import com.mcsturtletrackerbackend.inventory.api.AppInventoryWebSocketTextHandler;
import com.mcsturtletrackerbackend.inventory.api.McsInventoryWebSocketTextHandler;
import com.mcsturtletrackerbackend.main;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class InventoryWebSocketConfig implements WebSocketConfigurer {

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        AppInventoryWebSocketTextHandler appInventoryWebSocketTextHandler = main.getBean(AppInventoryWebSocketTextHandler.class);
        McsInventoryWebSocketTextHandler mcsInventoryWebSocketTextHandler = main.getBean(McsInventoryWebSocketTextHandler.class);

        registry.addHandler(mcsInventoryWebSocketTextHandler, "/ws/mcs/inventory").setAllowedOrigins("*");
        registry.addHandler(appInventoryWebSocketTextHandler, "/ws/app/inventory").setAllowedOrigins("*");

    }


}
