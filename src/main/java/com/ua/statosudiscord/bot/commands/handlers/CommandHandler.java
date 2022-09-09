package com.ua.statosudiscord.bot.commands.handlers;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.discordjson.json.ApplicationCommandRequest;
import reactor.core.publisher.Mono;
public abstract class CommandHandler {
    private ApplicationCommandRequest commandRequest;
    public abstract Mono<Void> handle(ChatInputInteractionEvent event);

    public ApplicationCommandRequest getCommandRequest() {
        return commandRequest;
    }

    public void setCommandRequest(ApplicationCommandRequest commandRequest) {
        this.commandRequest = commandRequest;
    }
}
