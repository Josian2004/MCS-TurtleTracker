package com.mcsturtletrackerbackend.locations.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.locations.model.Location;
import com.mcsturtletrackerbackend.locations.repository.LocationRepository;
import com.mcsturtletrackerbackend.locations.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class McsLocationsWebSocketTextHandlerIntTest {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private LocationRepository repository;

    @Autowired
    private LocationService service;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        service.wipeLocalStorage();
    }

    @Test
    void handleTextMessage() throws JsonProcessingException {
        Location expected = new Location(1L, 5, 5, 5, "Testing World");
        String payload = objectMapper.writeValueAsString(expected);
        try {
            Location location = objectMapper.readValue(payload, Location.class);
            service.newLocation(location);
        }
        catch (JsonProcessingException e){
            System.out.println("Invalid Json");
            fail();
        }
        if (repository.findById(1L).isPresent()) {
            Location actual = repository.findById(1L).get();
            assertEquals(expected, actual);
        } else {
            fail();
        }


    }
}