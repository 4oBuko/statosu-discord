package com.ua.statosudiscord;

import com.ua.statosudiscord.bot.slashcommands.CommandsManager;
import com.ua.statosudiscord.bot.slashcommands.commands.SlashCommandBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
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
    public JDA jda(CommandsManager manager) {
        JDA jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(manager);
        return jda;
    }
}
