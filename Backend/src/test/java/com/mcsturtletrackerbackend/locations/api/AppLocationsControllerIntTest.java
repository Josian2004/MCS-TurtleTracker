package com.mcsturtletrackerbackend.locations.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.locations.exceptions.LocationNotFoundException;
import com.mcsturtletrackerbackend.locations.model.Location;
import com.mcsturtletrackerbackend.locations.repository.LocationRepository;
import com.mcsturtletrackerbackend.locations.service.LocationService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AppLocationsControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private LocationRepository repository;

    @Autowired
    private AppLocationsController controller;

    @Autowired
    private LocationService service;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        service.wipeLocalStorage();
    }

    @Test
    void getLastLocation_ShouldBeEqual() {
        Location expectedLocation = new Location(1, 5, 5, 5, "Testing World");
        repository.save(expectedLocation);
        service.updateLocalStorage();

        Location actualLocation = null;
        try {
            actualLocation = controller.GetLastLocation(1);
            Assert.assertTrue(expectedLocation.equals(actualLocation));
        } catch (LocationNotFoundException e) {
            Assertions.fail();
        }
    }

    @Test
    void getLastLocationWithInvalidId_ShouldThrowLocationNotFoundException() {
        Assert.assertThrows(LocationNotFoundException.class, () -> controller.GetLastLocation(1));
    }
}