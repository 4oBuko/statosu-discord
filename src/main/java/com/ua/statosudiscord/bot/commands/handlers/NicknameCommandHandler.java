package com.ua.statosudiscord.bot.commands.handlers;

import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class NicknameCommandHandler extends CommandHandler {

    public NicknameCommandHandler() {
        ApplicationCommandRequest commandRequest = ApplicationCommandRequest.builder()
                .name("nickname")
                .description("set your osu! nickname")
                .addOption(ApplicationCommandOptionData.builder()
                        .name("nickname")
                        .description("your osu! nickname")
                        .type(ApplicationCommandOption.Type.STRING.getValue())
                        .required(true)
                        .build()
                )
                .build();
        setCommandRequest(commandRequest);
    }

    @Override
    public Mono<Void> handle() {
//        todo: add implementation
        return null;
    }
}
