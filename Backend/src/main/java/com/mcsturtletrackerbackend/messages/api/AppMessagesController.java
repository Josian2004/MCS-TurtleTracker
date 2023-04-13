package com.mcsturtletrackerbackend.messages.api;

import com.mcsturtletrackerbackend.messages.exceptions.MessagesNotFoundException;
import com.mcsturtletrackerbackend.messages.model.Message;
import com.mcsturtletrackerbackend.messages.model.MessageType;
import com.mcsturtletrackerbackend.messages.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("app/messages")
public class AppMessagesController {

    private final MessageService messageService;
    @Autowired
    public AppMessagesController(MessageService messageService){
        this.messageService = messageService;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessages() throws MessagesNotFoundException {
        return messageService.getAllMessages();
    }
    @GetMapping("/by")
    @ResponseStatus(HttpStatus.OK)
    public Message getMessageById(@RequestParam(value = "id")String id) throws MessagesNotFoundException {
        return messageService.getMessageById(id);
    }
    @GetMapping("/all/amount")
    @ResponseStatus(HttpStatus.OK)
    public Long getAmountMessages(){
        return messageService.getAmountMessages();
    }

    @PostMapping("/all/amount/by-types")
    @ResponseStatus(HttpStatus.OK)
    public Long getAmountMessagesByTypes(@RequestBody ArrayList<MessageType> messageTypes){
        return messageService.GetAmountLogsByTypes(messageTypes);
    }
    @PostMapping("/all/amount/by-types/by-computers")
    @ResponseStatus(HttpStatus.OK)
    public Long getAmountMessagesByComputerAndTypes(@RequestParam(value = "computerId")int computerId, @RequestBody ArrayList<MessageType> messageTypes){
        return messageService.getAmountMessagesByComputerAndTypes(computerId, messageTypes);
    }

    @GetMapping("/all/by-range")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessagesByRange(@RequestParam(value = "page")int page, @RequestParam(value = "size")int size) throws MessagesNotFoundException {
        return messageService.getAllMessagesByRange(page, size);
    }

    @PostMapping("/all/by-range/by-types")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessagesByTypesAndRange(@RequestParam(value = "page")int page, @RequestParam(value = "size")int size, @RequestBody ArrayList<MessageType> messageTypes) throws MessagesNotFoundException {
        return messageService.getAllMessagesByTypesAndRange(page, size, messageTypes);
    }

    @PostMapping("/all/by-range/by-types/by-computers")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessagesByTypesComputerAndRange(@RequestParam(value = "page")int page, @RequestParam(value = "size")int size, @RequestParam(value = "computerId")int computerId, @RequestBody ArrayList<MessageType> messageTypes) throws MessagesNotFoundException {
        return messageService.getAllMessagesByTypesComputerAndRange(page, size, messageTypes, computerId);
    }

    @GetMapping("/all/by-computer")
    @ResponseStatus(HttpStatus.OK)
    public List<Message> getAllMessagesByComputerId(@RequestParam(value = "computerId")String computerId){
        return Collections.unmodifiableList(new ArrayList<>());
    }
}