package com.ua.statosudiscord.bot.listeners.date;

import com.ua.statosudiscord.utils.MessageBuilder;
import com.ua.statosudiscord.bot.listeners.ProcessCommand;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.services.StatisticService;
import com.ua.statosudiscord.services.UserService;
import discord4j.core.object.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;

public abstract class DateListener implements ProcessCommand {

    Logger logger = LoggerFactory.getLogger(DateListener.class);
    @Autowired
    UserService userService;

    @Autowired
    StatisticService statisticService;

    @Override
    public Mono<Void> processCommand(Message eventMessage) {
//        todo: rewrite error messages
        String parametersError = "Parameters error.Use: !date frequency{daily, weekly or monthly} time {0-23}";
        String timeError = "Wrong time. Enter value in range 0-23";
        String wrongDayOfWeekError = "Wrong day of the week. Enter monday-sunday";
        String wrongNumberOfMonth = "Wrong number of the month. Enter value in range 1-28";
        String periodError = "Wrong period. Period could be daily, weekly or monthly";
        String[] arguments = eventMessage.getContent().split(" ");
        arguments[1] = arguments[1].toLowerCase();
        String response;
        if (arguments[0].equals("!update") && (arguments.length > 4 || arguments.length < 3)) {
            return Mono.just(eventMessage)
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage(parametersError))
                    .then();
        } else if (arguments[0].equals("!update")) {
            User existedUser = userService.getUser(eventMessage);
            int updateTime = 0;
            int numberOfMonth = 0;
            DayOfWeek dayOfWeek = null;
            UpdatePeriod updatePeriod;
            try {
                updatePeriod = UpdatePeriod.valueOf(arguments[1]);
            } catch (IllegalArgumentException e) {
                return Mono.just(eventMessage)
                        .flatMap(Message::getChannel)
                        .flatMap(channel -> channel.createMessage(periodError))
                        .then();
            }
            if (existedUser == null) {
                response = "You cannot use this operation. Use !nickname command first";
                return Mono.just(eventMessage)
                        .flatMap(Message::getChannel)
                        .flatMap(channel -> channel.createMessage(response))
                        .then();
            }
            if (arguments[1].equals("daily")) {
                try {
                    updateTime = Integer.parseInt(arguments[2]);
                    if (updateTime < 0 || updateTime > 23) {
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
                }
            }
            if (arguments[1].equals("weekly") || arguments[1].equals("monthly")) {
                try {
                    updateTime = Integer.parseInt(arguments[3]);
                } catch (NumberFormatException e) {
                    return Mono.just(eventMessage)
                            .flatMap(Message::getChannel)
                            .flatMap(channel -> channel.createMessage(timeError))
                            .then();
                }
                if (arguments[1].equals("weekly")) {
                    try {
                        dayOfWeek = DayOfWeek.valueOf(arguments[2].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        return Mono.just(eventMessage)
                                .flatMap(Message::getChannel)
                                .flatMap(channel -> channel.createMessage(wrongDayOfWeekError))
                                .then();
                    }
                } else {
                    try {
                        numberOfMonth = Integer.parseInt(arguments[2]);
                    } catch (NumberFormatException e) {
                        return Mono.just(eventMessage)
                                .flatMap(Message::getChannel)
                                .flatMap(channel -> channel.createMessage(wrongNumberOfMonth))
                                .then();
                    }
                }
            }
            User updatedUser = userService.changeUpdateInto(existedUser);
            updatedUser.setUpdatePeriod(updatePeriod);
            updatedUser.setUpdateTime(updateTime);
            updatedUser.setDayOfMonth(numberOfMonth);
            updatedUser.setDayOfWeek(dayOfWeek);
            Statistic statistic = statisticService.getNewestStatistic(updatedUser);
            return Mono.just(eventMessage)
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage(MessageBuilder.createMessage(statistic).getMessage()))
                    .then();
        }
        return Mono.empty();
    }
}
