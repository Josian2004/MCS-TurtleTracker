package com.mcsturtletrackerbackend.messages.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mcsturtletrackerbackend.ResponseObject;
import com.mcsturtletrackerbackend.messages.model.MessageType;
import com.mcsturtletrackerbackend.messages.model.ServiceMessage;
import com.mcsturtletrackerbackend.messages.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("mcs/messages")
public class McsMessagesController {
    private final MessageService service;

    @Autowired
    public McsMessagesController(MessageService service) {
        this.service = service;
    }

    @PostMapping("/services/send")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseObject sendServiceMessage(
            @RequestParam(value = "serviceName")String serviceName, @RequestParam(value = "message")String message, @RequestParam(value = "messageType")MessageType messageType, @RequestBody(required = false) Object metaData) {
        ServiceMessage serviceMessage;
        if (metaData != null) {
            serviceMessage = new ServiceMessage(message, metaData, messageType, serviceName);
        } else {
            serviceMessage = new ServiceMessage(message, messageType, serviceName);
        }

        try {
            service.newMessage(serviceMessage);
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The " + messageType + " message: '" + message + "' from service: " + serviceName + " was invalid");
            //return new ResponseObject(HttpStatus.BAD_REQUEST.value(), "The " + messageType + " message: '" + message + "' from service: " + serviceName + " was invalid");
        }
        return new ResponseObject(HttpStatus.OK.value(), "The " + messageType + " message: '" + message + "' from service: " + serviceName + " has been successfully send");
    }

}
