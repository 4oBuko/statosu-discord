package com.ua.statosudiscord.bot.commands.handlers;

import com.ua.statosudiscord.services.MessageService;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UsernameCommandHandler extends CommandHandler {
    private MessageService messageService;
    @Autowired
    public UsernameCommandHandler(MessageService messageService) {
        this.messageService = messageService;
        ApplicationCommandRequest commandRequest = ApplicationCommandRequest.builder()
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
