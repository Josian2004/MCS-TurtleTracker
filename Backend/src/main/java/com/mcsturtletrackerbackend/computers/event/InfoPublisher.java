package com.mcsturtletrackerbackend.computers.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InfoPublisher {
    private final ApplicationEventPublisher eventPublisher;

    InfoPublisher(ApplicationEventPublisher publisher) {
        this.eventPublisher = publisher;
    }

    public void publishNewComputerEvent(NewComputerEvent newComputerEvent){
        eventPublisher.publishEvent(newComputerEvent);
    }
}
