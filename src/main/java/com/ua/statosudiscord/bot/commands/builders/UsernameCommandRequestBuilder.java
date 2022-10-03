package com.ua.statosudiscord.bot.commands.builders;

import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;

public class UsernameCommandRequestBuilder implements CommandRequestBuilder {
    @Override
    public ApplicationCommandRequest buildCommandRequest() {
        return ApplicationCommandRequest.builder()
                .name("username")
                .description("set your osu! username")
                .addOption(ApplicationCommandOptionData.builder()
                        .name("username")
                        .description("your osu! username")
                        .type(ApplicationCommandOption.Type.STRING.getValue())
                        .required(true)
                        .build()
                )
                .build();
    }
}
