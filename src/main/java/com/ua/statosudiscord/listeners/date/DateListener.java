package com.ua.statosudiscord.listeners.date;

import com.ua.statosudiscord.UpdatePeriod;
import com.ua.statosudiscord.listeners.ProcessCommand;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class DateListener implements ProcessCommand {
    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        String parametersError = "Parameters error.Use: !date type{daily, weekly or monthly} time {0-23}";
        String timeError = "wrong time. 0-23";
        String periodError = "wrong period. daily, monthly, weekly";
        String[] commandWithArguments = eventMessage.getContent().split(" ");
        if (commandWithArguments[0].equals("!date") && commandWithArguments.length != 3) {
            return Mono.just(eventMessage)
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage(parametersError))
                    .then();
        } else if (commandWithArguments[0].equals("!date")) {
            try {
                UpdatePeriod updatePeriod = UpdatePeriod.valueOf(commandWithArguments[1]);
                int updateTime = Integer.parseInt(commandWithArguments[2]);
                if (!commandWithArguments[0].equals("!date") && (updateTime < 0 || updateTime > 23)) {
                    return Mono.just(eventMessage)
                            .flatMap(Message::getChannel)
                            .flatMap(channel -> channel.createMessage(timeError))
                            .then();
                }
            } catch (NumberFormatException e) {
                return Mono.just(eventMessage)
                        .flatMap(Message::getChannel)
                        .flatMap(channel -> channel.createMessage(timeError))
                        .then();
            } catch (IllegalArgumentException e) {
                return Mono.just(eventMessage)
                        .flatMap(Message::getChannel)
                        .flatMap(channel -> channel.createMessage(periodError))
                        .then();
            }
            return Mono.just(eventMessage).filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage("You will get your stat " + commandWithArguments[1] + " at " + commandWithArguments[2]))
                    .then();

        } else {
            return Mono.empty();
        }
    }
}
