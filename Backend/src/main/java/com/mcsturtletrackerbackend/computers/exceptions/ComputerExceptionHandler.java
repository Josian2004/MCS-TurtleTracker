package com.mcsturtletrackerbackend.computers.exceptions;


import com.mcsturtletrackerbackend.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ComputerExceptionHandler {
    @ExceptionHandler(value = ComputerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseObject handleComputerNotFoundException() {
        return new ResponseObject(HttpStatus.NOT_FOUND.value(), "Requested computer not found");
    }
}
