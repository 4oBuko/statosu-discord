package com.ua.statosudiscord.bot;

import com.ua.statosudiscord.bot.listeners.EventListener;
import com.ua.statosudiscord.persistence.SequenceGeneratorService;
import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.persistence.repositories.StatisticRepository;
import com.ua.statosudiscord.persistence.repositories.UserRepository;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BotConfiguration {

    @Value("${bot.token}")
    private String token;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    SequenceGeneratorService generatorService;

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {
        GatewayDiscordClient client = DiscordClientBuilder
                .create(token)
                .build()
                .login()
                .block();

        for (EventListener<T> listener : eventListeners) {
            client.on(listener.getEventType())
                    .flatMap(listener::execute)
                    .onErrorResume(listener::handleError)
                    .subscribe();
        }
//        todo: what is that?
        User user = new User(1L,1L,"test");
        user.setId(generatorService.generateSequence(User.SEQUENCE_NAME));
        userRepository.save(user);
        StatisticBuilder statisticBuilder = new StatisticBuilder()
                .setUser(user)
                .setId(generatorService.generateSequence(Statistic.SEQUENCE_NAME));
        statisticRepository.save(statisticBuilder.setUser(user).build());
        return client;
    }
}
