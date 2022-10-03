package com.ua.statosudiscord.bot.commands.builders;

import discord4j.discordjson.json.ApplicationCommandRequest;

public class AboutCommandRequestBuilder implements CommandRequestBuilder{
    @Override
    public ApplicationCommandRequest buildCommandRequest() {
        return ApplicationCommandRequest.builder()
                .name("about")
                .description("info about me")
                .build();
    }
}
