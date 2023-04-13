package com.mcsturtletrackerbackend.locations.config;

import com.mcsturtletrackerbackend.locations.api.AppLocationsWebSocketTextHandler;
import com.mcsturtletrackerbackend.locations.api.McsLocationsWebSocketTextHandler;
import com.mcsturtletrackerbackend.main;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class LocationsWebSocketConfig implements WebSocketConfigurer {

    private McsLocationsWebSocketTextHandler mcsLocationsWebSocketTextHandler = main.getBean(McsLocationsWebSocketTextHandler.class);
    private AppLocationsWebSocketTextHandler appLocationsWebSocketTextHandler = main.getBean(AppLocationsWebSocketTextHandler.class);

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(mcsLocationsWebSocketTextHandler, "/ws/mcs/locations").setAllowedOrigins("*");
        registry.addHandler(appLocationsWebSocketTextHandler, "/ws/app/locations").setAllowedOrigins("*");;
    }


}
