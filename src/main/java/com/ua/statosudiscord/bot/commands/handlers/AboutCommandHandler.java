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
    private final static String aboutBot = """
            Hello, I'm Statosu. I show your progress in osu! classic mode.
            You need to give me your osu! username and the update time.
            I support these commands:
            - /about - about me
            - /username - set your osu! username
            - /update - set time for statistic update
            """;
    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        return event.reply()
                .withEphemeral(true)
                .withContent(aboutBot);
    }
}
