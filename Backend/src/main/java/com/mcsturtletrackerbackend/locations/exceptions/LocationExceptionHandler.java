package com.mcsturtletrackerbackend.locations.exceptions;


import com.mcsturtletrackerbackend.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LocationExceptionHandler {

    @ExceptionHandler(value = LocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseObject handleLocationNotFoundException(){
        return new ResponseObject(HttpStatus.NOT_FOUND.value(), "Location not found");
    }
}
