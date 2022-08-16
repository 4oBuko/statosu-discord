package com.ua.statosudiscord.bot.listeners.about;

import com.ua.statosudiscord.bot.listeners.EventListener;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AboutCreateListener extends AboutListener implements EventListener<MessageCreateEvent> {
    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return processCommand(event.getMessage());
    }
}
