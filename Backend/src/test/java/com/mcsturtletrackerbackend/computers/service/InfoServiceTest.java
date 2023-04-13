package com.mcsturtletrackerbackend.computers.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.computers.model.Computer;
import com.mcsturtletrackerbackend.computers.repository.ComputerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class InfoServiceTest {

    @Autowired
    private MockMvc mockMvc;

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
    void oldComputerIsReplacedWithNewInfo() {
        Computer expectedComputer1 = new Computer(1, "Test 1", 0,  "Testing", "Turtle", 1000, 10000, LocalDateTime.now().toString());
        Computer expectedComputer2 = new Computer(1, "Test 2", 0,  "Testing", "Turtle", 1000, 10000, LocalDateTime.now().toString());
        Computer actualComputer;

        try {
            service.newComputer(expectedComputer1);
            if (repository.findById(1L).isPresent()) {
                actualComputer = repository.findById(1L).get();
                assertEquals(expectedComputer1, actualComputer);
                assertNotEquals(expectedComputer2, actualComputer);
            }

            service.newComputer(expectedComputer2);
            if (repository.findById(1L).isPresent()) {
                actualComputer = repository.findById(1L).get();
                assertNotEquals(expectedComputer1, actualComputer);
                assertEquals(expectedComputer2, actualComputer);
            }
        }
        catch (JsonProcessingException e) {
            fail();
        }

    }
}