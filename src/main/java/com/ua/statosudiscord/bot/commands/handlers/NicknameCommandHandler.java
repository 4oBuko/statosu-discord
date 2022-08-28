package com.ua.statosudiscord.bot.commands.handlers;

import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.services.UserService;
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
public class NicknameCommandHandler extends CommandHandler {
    @Autowired
    UserService userService;

    public NicknameCommandHandler() {
        ApplicationCommandRequest commandRequest = ApplicationCommandRequest.builder()
                .name("nickname")
                .description("set your osu! nickname")
                .addOption(ApplicationCommandOptionData.builder()
                        .name("nickname")
                        .description("your osu! nickname")
                        .type(ApplicationCommandOption.Type.STRING.getValue())
                        .required(true)
                        .build()
                )
                .build();
        setCommandRequest(commandRequest);
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String nickname = event.getOption("nickname")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();
        String response;
        long channelId = event.getInteraction().getChannelId().asLong();
        long userId = event.getInteraction().getUser().getId().asLong();
        User existedUser = userService.getUser(channelId, userId);
        if (existedUser != null && existedUser.getOsuUsername().equals(nickname)) {
            response = nickname + " is already your nickname";
        } else if (existedUser != null && !existedUser.getOsuUsername().equals(nickname)) {
            String oldNickname = existedUser.getOsuUsername();
            existedUser = userService.updateUsername(channelId, userId, nickname);
            response = "Old username: " + oldNickname + "\n new username: " + existedUser.getOsuUsername();
        } else {
            existedUser = userService.addNewUser(channelId, userId, nickname);
            response = "Welcome. Your osu username is " + existedUser.getOsuUsername();
        }
        return event.reply()
                .withEphemeral(true)
                .withContent(response);
    }
}
