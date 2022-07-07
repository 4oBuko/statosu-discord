package com.ua.statosudiscord.bot.listeners.about;

import com.ua.statosudiscord.bot.listeners.EventListener;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AboutUpdateListener extends AboutListener implements EventListener<MessageUpdateEvent> {
    @Override
    public Class<MessageUpdateEvent> getEventType() {
        return MessageUpdateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageUpdateEvent event) {
        return Mono.just(event)
                .filter(MessageUpdateEvent::isContentChanged)
                .flatMap(MessageUpdateEvent::getMessage)
                .flatMap(super::processCommand);
    }
}
