package com.mcsturtletrackerbackend.messages.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcsturtletrackerbackend.messages.exceptions.MessagesNotFoundException;
import com.mcsturtletrackerbackend.messages.event.MessagesPublisher;
import com.mcsturtletrackerbackend.messages.event.NewMessageEvent;
import com.mcsturtletrackerbackend.messages.model.ComputerMessage;
import com.mcsturtletrackerbackend.messages.model.Message;
import com.mcsturtletrackerbackend.messages.model.MessageType;
import com.mcsturtletrackerbackend.messages.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private ArrayList<Message> messages;
    private final MessageRepository repository;
    private final MessagesPublisher messagesPublisher;

    @Autowired
    public MessageService(MessageRepository repository, MessagesPublisher messagesPublisher) {
        messages = new ArrayList();
        this.repository = repository;
        this.messagesPublisher = messagesPublisher;
    }

    public List<Message> getAllMessagesByRange(int page, int size) throws MessagesNotFoundException {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationDateTime");
        List<Message> foundMessages = repository.findAll(pageable).getContent();
        if (!foundMessages.isEmpty()) {
            return Collections.unmodifiableList(foundMessages);
        } else {
            throw new MessagesNotFoundException();
        }
    }

    public List<Message> getAllMessages() throws MessagesNotFoundException {
        List<Message> foundMessages = repository.findAll();
        if (!foundMessages.isEmpty()) {
            return Collections.unmodifiableList(foundMessages);
        } else {
            throw new MessagesNotFoundException();
        }
    }

    public Message getMessageById(String id) throws MessagesNotFoundException {
        Optional<Message> foundMessage = repository.findById(id);
        if (foundMessage.isPresent()) {
            return foundMessage.get();
        } else {
            throw new MessagesNotFoundException();
        }
    }
    public List<Message> getAllMessagesByTypesAndRange(int page, int size, ArrayList<MessageType> messageTypeList) throws MessagesNotFoundException {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationDateTime");
        List<Message> foundMessages = repository.findAllByMessageTypeIn(messageTypeList, pageable);
        if (!foundMessages.isEmpty()){
            return Collections.unmodifiableList(foundMessages);
        } else {
            throw new MessagesNotFoundException();
        }
    }

    public String convertToJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public long getAmountMessages(){
        return repository.count();
    }
    public long GetAmountLogsByTypes(ArrayList<MessageType> messageTypeList) {
        return repository.countAllByMessageTypeIn(messageTypeList);
    }

    public long getAmountMessagesByComputerAndTypes(int computerId, ArrayList<MessageType> messageTypeList) {
        return repository.countAllByComputerIdAndMessageTypeIn(computerId, messageTypeList);
    }



    public void newMessage(Message message) throws JsonProcessingException {
        messages.add(message);
        repository.save(message);
        messagesPublisher.PublishNewLogEvent(new NewMessageEvent(convertToJson(message)));
    }


    public List<Message> getAllMessagesByTypesComputerAndRange(int page, int size, ArrayList<MessageType> messageTypes, int computerId) throws MessagesNotFoundException {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "creationDateTime");
        List<ComputerMessage> foundMessages = repository.findAllByComputerIdAndMessageTypeIn(computerId, messageTypes, pageable);
        if (!foundMessages.isEmpty()) {
            return Collections.unmodifiableList(foundMessages);
        } else {
            throw new MessagesNotFoundException();
        }
    }
}
