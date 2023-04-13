package com.mcsturtletrackerbackend.messages.exceptions;


import com.mcsturtletrackerbackend.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MessagesExceptionHandler {
    @ExceptionHandler(value = MessagesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseObject handleLogsNotFoundException(){
        return new ResponseObject(HttpStatus.NOT_FOUND.value(), "Logs not found");
    }
}
