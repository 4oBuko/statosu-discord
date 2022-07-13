package com.ua.statosudiscord.bot.listeners.date;

import com.ua.statosudiscord.bot.MessageBuilder;
import com.ua.statosudiscord.bot.listeners.ProcessCommand;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.services.StatisticService;
import com.ua.statosudiscord.services.UserService;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public abstract class DateListener implements ProcessCommand {
    @Autowired
    UserService userService;

    @Autowired
    StatisticService statisticService;

    @Override
    public Mono<Void> processCommand(Message eventMessage) {
        String parametersError = "Parameters error.Use: !date frequency{daily, weekly or monthly} time {0-23}";
        String timeError = "Wrong time.Enter value in range 0-23";
        String periodError = "Wrong period.Period could be daily, weekly or monthly";
        String[] commandWithArguments = eventMessage.getContent().split(" ");
        String response;
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
//            check if user exists
            User existedUser = userService.getUser(eventMessage);
            if (existedUser == null) {
                response = "You cannot use this operation. Use !nickname command first";
            } else {
                Statistic statistic = statisticService.updateUserStatistic(existedUser);
                    response = MessageBuilder.createMessage(statistic).getMessage();
            }
            return Mono.just(eventMessage).filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage(response))
                    .then();
//"You will get your stat " + commandWithArguments[1] + " at " + commandWithArguments[2]
        } else {
            return Mono.empty();
        }
    }
}
