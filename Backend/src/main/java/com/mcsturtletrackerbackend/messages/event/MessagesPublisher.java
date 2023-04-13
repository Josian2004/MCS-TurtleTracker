package com.mcsturtletrackerbackend.messages.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MessagesPublisher {
    private final ApplicationEventPublisher eventPublisher;

    MessagesPublisher(ApplicationEventPublisher publisher) {
        this.eventPublisher = publisher;
    }

    public void PublishNewLogEvent(NewMessageEvent newMessageEvent){
        eventPublisher.publishEvent(newMessageEvent);
    }
}
