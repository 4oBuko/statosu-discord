package com.ua.statosudiscord.bot.commands.handlers;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.discordjson.json.ApplicationCommandRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AboutCommandHandler extends CommandHandler {

    public AboutCommandHandler() {
        ApplicationCommandRequest request = ApplicationCommandRequest.builder()
                .name("about")
                .description("info about me")
                .build();

        setCommandRequest(request);
    }
    private final static String aboutBot = "Hello, I'm Statosu. " +
            "I show your progress in osu! classic mode.\n" +
            "You need to give me your osu! nickname and the update time.\n" +
            "I support these commands:\n" +
            "- /about - about me\n" +
            "- /nickname - set your osu! nickname\n" +
            "- /update - set time for statistic update\n";
    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        return event.reply()
                .withEphemeral(true)
                .withContent(aboutBot);
    }
}
