package com.ua.statosudiscord.listeners.about;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class AboutListener {
    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!about"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Add about message and logic"))
                .then();
    }
}
