package com.mcsturtletrackerbackend.messages.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.messages.service.MessageService;
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
class McsMessagesWebSocketIntTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private LogRepository repository;

    @Autowired
    private AppMessagesController controller;

    @Autowired
    private MessageService service;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void handleTextMessage() {
    }
}