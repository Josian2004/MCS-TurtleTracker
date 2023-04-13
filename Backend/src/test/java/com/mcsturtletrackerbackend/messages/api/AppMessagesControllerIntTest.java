package com.mcsturtletrackerbackend.messages.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.messages.exceptions.MessagesNotFoundException;
import com.mcsturtletrackerbackend.messages.model.Message;
import com.mcsturtletrackerbackend.messages.model.MessageType;
import com.mcsturtletrackerbackend.messages.service.MessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class AppMessagesControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private LogRepository repository;

    @Autowired
    private AppMessagesController controller;

    @Autowired
    private MessageService service;

    private List<Log> mock;

    @BeforeEach
    void setup() {
        repository.deleteAll();
        mock = new ArrayList<>();
        Log log1 = new Log(1, MessageType.Error, "Test");
        Log log2 = new Log(1, MessageType.Error, "Test");
        Log log3 = new Log(1, MessageType.Warning, "Test");
        Log log4 = new Log(1, MessageType.Warning, "Test");
        Log log5 = new Log(1, MessageType.Info, "Test");

        Log log6 = new Log(2, MessageType.Info, "Test");
        Log log7 = new Log(2, MessageType.Warning, "Test");
        Log log8 = new Log(2, MessageType.Error, "Test");
        Log log9 = new Log(2, MessageType.Debug, "Test");
        Log log10 = new Log(2, MessageType.Debug, "Test");

        Log log11 = new Log(3, MessageType.Warning, "Test");
        Log log12 = new Log(3, MessageType.Warning, "Test");
        Log log13 = new Log(3, MessageType.Warning, "Test");
        Log log14 = new Log(3, MessageType.Error, "Test");
        Log log15 = new Log(3, MessageType.Info, "Test");

        Log log16 = new Log(4, MessageType.Error, "Test");
        Log log17 = new Log(4, MessageType.Error, "Test");
        Log log18 = new Log(4, MessageType.Debug, "Test");
        Log log19 = new Log(4, MessageType.Info, "Test");
        Log log20 = new Log(4, MessageType.Debug, "Test");

        Log log21 = new Log(5, MessageType.Info, "Test");
        Log log22 = new Log(5, MessageType.Info, "Test");
        Log log23 = new Log(5, MessageType.Debug, "Test");
        Log log24 = new Log(5, MessageType.Debug, "Test");
        Log log25 = new Log(5, MessageType.Warning, "Test");

        Log log26 = new Log(6, MessageType.Error, "Test");
        Log log27 = new Log(6, MessageType.Error, "Test");
        Log log28 = new Log(6, MessageType.Error, "Test");
        Log log29 = new Log(6, MessageType.Error, "Test");
        Log log30 = new Log(6, MessageType.Error, "Test");

        mock.add(log1); mock.add(log2);mock.add(log3);mock.add(log4);mock.add(log5);mock.add(log6);mock.add(log7);mock.add(log8);mock.add(log9);mock.add(log10);
        mock.add(log11);mock.add(log12);mock.add(log13);mock.add(log14);mock.add(log15);mock.add(log16);mock.add(log17);mock.add(log18);mock.add(log19);mock.add(log20);
        mock.add(log21);mock.add(log22);mock.add(log23);mock.add(log24);mock.add(log25);mock.add(log26);mock.add(log27);mock.add(log28);mock.add(log29);mock.add(log30);

        repository.saveAll(mock);
    }


    @Test
    void getAllLogs() {
        try {
            List<Log> actual = null;
            actual = controller.getAllMessages();
            Assertions.assertEquals(mock.size(), actual.size());
            Assertions.assertArrayEquals(mock.toArray(), actual.toArray());
        } catch (MessagesNotFoundException e) {
            Assertions.fail();
        }


    }

    @Test
    void getAmountLogs() {
        Long expected = (long) mock.size();
        Long actual = controller.getAmountMessages();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAmountLogsByTypes() {
        Long expected = 18L;
        ArrayList<MessageType> actualTypes = new ArrayList<>();
        actualTypes.add(MessageType.Error); actualTypes.add(MessageType.Warning);
        Long actual = controller.getAmountMessagesByTypes(actualTypes);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAmountLogsByComputerAndTypes() {
        Long expected = 3L;
        ArrayList<MessageType> actualTypes = new ArrayList<>();
        actualTypes.add(MessageType.Info); actualTypes.add(MessageType.Debug);

        Long actual = controller.getAmountMessagesByComputerAndTypes(4, actualTypes);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllLogsByRange_Page0() {
        List<Log> expected = new ArrayList<>();
        int expectedSize = 5;
        int expectedPage = 0;
        for (int i = 25; i < 30; i++) {
            expected.add(mock.get(i));
        }
        try {
            List<Message> actual = null;
            actual = controller.getAllMessagesByRange(expectedPage, expectedSize);
            Assertions.assertEquals(expected.size(), actual.size());
            Assertions.assertArrayEquals(expected.toArray(), actual.toArray());
        } catch (MessagesNotFoundException e) {
            Assertions.fail();
        }
    }

    @Test
    void getAllLogsByRange_Page1() {
        List<Log> expected = new ArrayList<>();
        int expectedSize = 5;
        int expectedPage = 1;
        for (int i = 20; i < 25; i++) {
            expected.add(mock.get(i));
        }
        Collections.reverse(expected);

        try {
            List<Message> actual = null;
            actual = controller.getAllMessagesByRange(expectedPage, expectedSize);
            Assertions.assertEquals(expected.size(), actual.size());
            Assertions.assertArrayEquals(expected.toArray(), actual.toArray());
        } catch (MessagesNotFoundException e) {
            Assertions.fail();
        }
    }

    @Test
    void getAllLogsByTypesAndRange() {
        ArrayList<MessageType> actualTypes = new ArrayList<>();
        int expectedSize = 5;
        int expectedPage = 0;
        actualTypes.add(MessageType.Warning); actualTypes.add(MessageType.Debug);
        List<Log> expected = new ArrayList<>();
        expected.add(mock.get(24));
        expected.add(mock.get(23));
        expected.add(mock.get(22));
        expected.add(mock.get(19));
        expected.add(mock.get(17));


        try {
            List<Message> actual = null;
            actual = controller.getAllMessagesByTypesAndRange(expectedPage, expectedSize, actualTypes);
            Assertions.assertEquals(expected.size(), actual.size());
            Assertions.assertArrayEquals(expected.toArray(), actual.toArray());
        } catch (MessagesNotFoundException e) {
            Assertions.fail();
        }



    }

    @Test
    void getAllLogsByTypesComputerAndRange() {
        ArrayList<MessageType> actualTypes = new ArrayList<>();
        int expectedSize = 10;
        int expectedPage = 0;
        actualTypes.add(MessageType.Info); actualTypes.add(MessageType.Debug);
        List<Log> expected = new ArrayList<>();
        expected.add(mock.get(23));
        expected.add(mock.get(22));
        expected.add(mock.get(21));
        expected.add(mock.get(20));


        try {
            List<Message> actual = null;
            actual = controller.getAllMessagesByTypesComputerAndRange(expectedPage, expectedSize,5, actualTypes);
            Assertions.assertEquals(expected.size(), actual.size());
            Assertions.assertArrayEquals(expected.toArray(), actual.toArray());
        } catch (MessagesNotFoundException e) {
            Assertions.fail();
        }


    }
}