package com.ua.statosudiscord.listeners.nickname;

import com.ua.statosudiscord.listeners.ProcessCommand;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class NicknameListener implements ProcessCommand {
    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        String parametersError = "Wrong request.\\n use: !nickname your_nickname";
        String wrongNickname = "Nickname not found";
        String[] commandWithParameters = eventMessage.getContent().split(" ");
        boolean isFound = true;//add function for checking nickname in Osu
        Mono<Void> response;
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
            return Mono.just(eventMessage)
                    .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage("Your nickname is : " + commandWithParameters[1] ))
                    .then();
        }
        else {
            return Mono.empty();
        }
    }
}
