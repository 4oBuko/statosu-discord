package com.ua.statosudiscord;

import com.ua.statosudiscord.bot.commands.handlers.CommandHandler;
import com.ua.statosudiscord.persistence.SequenceGeneratorService;
import com.ua.statosudiscord.persistence.repositories.StatisticRepository;
import com.ua.statosudiscord.persistence.repositories.UserRepository;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import discord4j.discordjson.json.ApplicationCommandRequest;
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

    final UserRepository userRepository;

    final StatisticRepository statisticRepository;

    final SequenceGeneratorService generatorService;

    public BotConfiguration(@Value("${bot.token}") String token, UserRepository userRepository, StatisticRepository statisticRepository, SequenceGeneratorService generatorService) {
        this.token = token;
        this.userRepository = userRepository;
        this.statisticRepository = statisticRepository;
        this.generatorService = generatorService;
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
}
