package com.mcsturtletrackerbackend.computers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.computers.exceptions.ComputerNotFoundException;
import com.mcsturtletrackerbackend.computers.model.Computer;
import com.mcsturtletrackerbackend.computers.repository.ComputerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AppInfoControllerIntTest {


    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private ComputerRepository repository;

    @Autowired
    private AppInfoController controller;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    public String convertToJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllComputers_ShouldBeEqual() {
        List<Computer> expectedComputers = new ArrayList<>();
        String status = "Testing";
        String computerLabel = "Test1";
        String device = "Turtle";
        expectedComputers.add(new Computer(1, computerLabel, 0,  status, device, 1000, 10000, LocalDateTime.now().toString()));
        expectedComputers.add(new Computer(2, computerLabel, 0,  status, device, 1000, 10000, LocalDateTime.now().toString()));
        expectedComputers.add(new Computer(3, computerLabel, 0,  status, device, 1000, 10000, LocalDateTime.now().toString()));
        expectedComputers.add(new Computer(4, computerLabel, 0,  status, device, 1000, 10000, LocalDateTime.now().toString()));
        repository.saveAll(expectedComputers);

        List<Computer> actualComputers = controller.getAllComputers();

        Assertions.assertEquals(expectedComputers.size(), actualComputers.size());
        Assertions.assertArrayEquals(expectedComputers.toArray(), actualComputers.toArray());
    }

    @Test
    void getComputerById_ShouldBeEqual() {
        Computer expectedComputer = new Computer(1, "Test 1", 0,  "Testing", "Turtle", 1000, 10000, LocalDateTime.now().toString());
        repository.save(expectedComputer);

        Computer actualComputer = null;
        try {
            actualComputer = controller.getComputerById(1);
            Assertions.assertEquals(expectedComputer, actualComputer);
        } catch (ComputerNotFoundException e) {
            Assertions.fail();
        }

    }

    @Test
    void getComputerByIdWithInvalidId_ShouldThrowComputerNotFoundException() {
            Assert.assertThrows(ComputerNotFoundException.class, () -> controller.getComputerById(1));
    }
}