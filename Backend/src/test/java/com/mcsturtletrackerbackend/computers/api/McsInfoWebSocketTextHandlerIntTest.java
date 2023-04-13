package com.mcsturtletrackerbackend.computers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.computers.model.Computer;
import com.mcsturtletrackerbackend.computers.repository.ComputerRepository;
import com.mcsturtletrackerbackend.computers.service.InfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class McsInfoWebSocketTextHandlerIntTest {

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    private ComputerRepository repository;

    @Autowired
    private InfoService service;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void handleTextMessage() {
        String payload = "{\"computerId\":\"1\",\"computerLabel\":\"Test 1\",\"mcsSystemId\":0,\"status\":\"Testing\",\"device\":\"Turtle\",\"fuelLevel\":15000,\"fuelLimit\":50000}";
        Computer expectedComputer = new Computer(1, "Test 1", 0, "Testing", "Turtle", 15000, 50000, "");
        try {
            Computer computer = objectMapper.readValue(payload, Computer.class);
            service.newComputer(computer);
        }
        catch (JsonProcessingException e){
            System.out.println("Invalid Json");
            fail();
        }
        if (repository.findById(1L).isPresent()) {
            Computer actualComputer = repository.findById(1L).get();
            assertEquals(expectedComputer, actualComputer);
        } else {
            fail();
        }


    }

}