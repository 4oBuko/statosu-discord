package com.ua.statosudiscord;

import com.ua.statosudiscord.bot.commands.handlers.CommandHandler;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.discordjson.json.ApplicationCommandRequest;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class BotConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(BotConfiguration.class);
    private final String token;

    public BotConfiguration(@Value("${bot.token}") String token) {
        this.token = token;
    }

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<CommandHandler> commandHandlers) {
        GatewayDiscordClient client = DiscordClientBuilder
                .create(token)
                .build()
                .login()
                .block();

        List<ApplicationCommandRequest> commandRequests = commandHandlers.stream().map(CommandHandler::getCommandRequest).collect(Collectors.toList());
        Long applicationId = client.getRestClient().getApplicationId().block();
        client.getRestClient()
                .getApplicationService()
                .bulkOverwriteGlobalApplicationCommand(applicationId, commandRequests)
                .doOnNext(ignore -> logger.debug("Slash commands successfully registered"))
                .doOnError(error -> logger.error("Fail to register slash commands. Error message: " + error.getMessage()))
                .subscribe();
        return client;
    }

    @Bean
    public JDA jda() {
        return JDABuilder.createDefault(token).build();
    }
}
