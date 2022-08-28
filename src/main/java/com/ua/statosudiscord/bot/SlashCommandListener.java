package com.ua.statosudiscord.bot;

import com.ua.statosudiscord.bot.commands.handlers.CommandHandler;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class SlashCommandListener {
    private List<CommandHandler> commandHandlers;

    public SlashCommandListener(List<CommandHandler> commandHandlers, GatewayDiscordClient discordClient) {
        this.commandHandlers = commandHandlers;
        discordClient.on(ChatInputInteractionEvent.class, this::handle).subscribe();
    }

    public Mono<Void> handle(ChatInputInteractionEvent event) {
        return Flux.fromIterable(commandHandlers)
                .filter(command -> command.getCommandRequest().name().equals(event.getCommandName()))
                .next()
                .flatMap(command -> command.handle(event));
    }
}
