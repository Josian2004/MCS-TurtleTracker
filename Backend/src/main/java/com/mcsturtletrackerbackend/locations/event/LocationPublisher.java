package com.mcsturtletrackerbackend.locations.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class LocationPublisher {
    private final ApplicationEventPublisher eventPublisher;

    LocationPublisher(ApplicationEventPublisher publisher) {
        this.eventPublisher = publisher;
    }

    public void PublishNewLocationEvent(NewLocationEvent newLocationEvent){
        eventPublisher.publishEvent(newLocationEvent);
    }
}
