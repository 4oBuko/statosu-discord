package com.ua.statosudiscord.bot.listeners;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public interface ProcessCommand {
    Mono<Void> processCommand(Message eventMessage);
}
