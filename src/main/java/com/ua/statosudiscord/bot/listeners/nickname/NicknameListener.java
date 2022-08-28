package com.ua.statosudiscord.bot.listeners.nickname;

import com.ua.statosudiscord.bot.listeners.ProcessCommand;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.services.StatisticService;
import com.ua.statosudiscord.services.UserService;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public abstract class NicknameListener implements ProcessCommand {
    @Autowired
    private UserService userService;

    @Autowired
    private StatisticService statisticService;

    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        String parametersError = "Wrong request.\\n use: !nickname your_nickname";
        String wrongNickname = "Nickname not found";
        String[] commandWithParameters = eventMessage.getContent().split(" ");
        String response;
        boolean isFound = true;//todo:add function for checking nickname in Osu
        if (eventMessage.getContent().startsWith("!nickname") && (commandWithParameters.length != 2)) {
            return Mono.just(eventMessage)
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage(parametersError))
                    .then();
        } else if (eventMessage.getContent().startsWith("!nickname") && !isFound) {
            return Mono.just(eventMessage)
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage(wrongNickname))
                    .then();
        } else if (eventMessage.getContent().startsWith("!nickname")) {
            User existedUser = userService.getUser(eventMessage.getChannelId().asLong(), eventMessage.getUserData().id().asLong());
            if (existedUser != null && existedUser.getOsuUsername().equals(commandWithParameters[1])) {
                response = commandWithParameters[1] + " is already your nickname";
            } else if (existedUser != null && !existedUser.getOsuUsername().equals(commandWithParameters[1])) {
                String oldNickname = existedUser.getOsuUsername();
                existedUser = userService.updateUsername(eventMessage.getChannelId().asLong(), eventMessage.getUserData().id().asLong(), commandWithParameters[1]);
                response = "Old username: " + oldNickname + "\n new username: " + existedUser.getOsuUsername();
            } else {
                existedUser = userService.addNewUser(eventMessage.getChannelId().asLong(), eventMessage.getUserData().id().asLong(), commandWithParameters[1]);
                response = "Welcome. Your osu username is " + existedUser.getOsuUsername();
            }
            return Mono.just(eventMessage)
                    .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage(response))
                    .then();
        } else {
            return Mono.empty();
        }
    }
}
