package com.ua.statosudiscord.bot.commands.builders;

import discord4j.discordjson.json.ApplicationCommandRequest;

public interface CommandRequestBuilder {
    ApplicationCommandRequest buildCommandRequest();
}
