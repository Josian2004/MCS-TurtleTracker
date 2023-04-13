package com.mcsturtletrackerbackend.inventory.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InventoryPublisher {
    private final ApplicationEventPublisher eventPublisher;

    InventoryPublisher(ApplicationEventPublisher publisher) {
        this.eventPublisher = publisher;
    }

    public void PublishNewInventoryEvent(NewInventoryEvent newInventoryEvent){
        eventPublisher.publishEvent(newInventoryEvent);
    }

    public void PublishRequestInventoryEvent(RequestInventoryEvent requestInventoryEvent){
        eventPublisher.publishEvent(requestInventoryEvent);
    }
}
