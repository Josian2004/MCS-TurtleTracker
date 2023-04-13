package com.mcsturtletrackerbackend.locations.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.locations.model.Location;
import com.mcsturtletrackerbackend.locations.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class LocationServiceTest {
    @Autowired
    private MockMvc mockMvc;

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
    void oldLocationIsReplacedWithNewLocation() {
        Location expected1 = new Location(1L, 5, 5, 5, "Testing World");
        Location expected2 = new Location(1L, 6, 6, 6, "Testing World");
        Location actual;

        try {
            service.newLocation(expected1);
            service.updateLocalStorage();
            if (repository.findById(1L).isPresent()) {
                actual = repository.findById(1L).get();
                assertEquals(expected1, actual);
                assertNotEquals(expected2, actual);
            }

            service.newLocation(expected2);
            service.saveLocationsToDB();
            service.updateLocalStorage();
            if (repository.findById(1L).isPresent()) {
                actual = repository.findById(1L).get();
                assertNotEquals(expected1, actual);
                assertEquals(expected2, actual);
            }
        }
        catch (JsonProcessingException e) {
            fail();
        }

    }

}