package com.mcsturtletrackerbackend;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingPongController {
    @GetMapping(value = "/ping")
    @ResponseStatus(HttpStatus.OK)
    public String PingPong() {
        return "pong";
    }
}
