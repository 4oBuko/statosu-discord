package com.ua.statosudiscord;

import com.ua.statosudiscord.bot.commands.handlers.CommandHandler;
import com.ua.statosudiscord.bot.slashcommands.commands.SlashCommandBuilder;
import com.ua.statosudiscord.bot.slashcommands.listeners.AboutCommandListener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BotConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(BotConfiguration.class);
    private final String token;

    public BotConfiguration(@Value("${bot.token}") String token) {
        this.token = token;
    }

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<CommandHandler> commandHandlers) {
        return DiscordClientBuilder
                .create(token)
                .build()
                .login()
                .block();
    }

    @Bean
    public JDA jda(List<ListenerAdapter> adapters) {
        JDA jda = JDABuilder.createDefault(token).build();
        adapters.forEach(jda::addEventListener);
        return jda;
    }
}
