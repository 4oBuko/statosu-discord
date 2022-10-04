package com.ua.statosudiscord.bot.commands.handlers;

import com.ua.statosudiscord.bot.commands.builders.UsernameCommandRequestBuilder;
import com.ua.statosudiscord.services.MessageService;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.discordjson.json.ApplicationCommandRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UsernameCommandHandler extends CommandHandler {
    private final MessageService messageService;

    @Autowired
    public UsernameCommandHandler(MessageService messageService) {
        this.messageService = messageService;
        UsernameCommandRequestBuilder usernameCommandRequestBuilder = new UsernameCommandRequestBuilder();
        ApplicationCommandRequest commandRequest = usernameCommandRequestBuilder.buildCommandRequest();
        setCommandRequest(commandRequest);
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String username = event.getOption("username")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();
        long channelId = event.getInteraction().getChannelId().asLong();
        long userId = event.getInteraction().getUser().getId().asLong();
        String response = messageService.updateUsername(channelId, userId, username);
        return event.reply()
                .withEphemeral(true)
                .withContent(response);
    }
}
