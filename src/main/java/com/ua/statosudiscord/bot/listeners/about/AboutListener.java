package com.ua.statosudiscord.bot.listeners.about;

import com.ua.statosudiscord.bot.listeners.ProcessCommand;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class AboutListener implements ProcessCommand {
    private final static String aboutBot = "I'm Statosu. " +
            "I show your progress in osu! classic mode.\n" +
            "You need to give me your osu! nickname and the update time.\n" +
            "I support these commands:\n" +
            "- !about - get commands list\n" +
            "- !nickname your-nickname - set osu! nickname to get info\n" +
            "- !update [monthly, weekly, daily] [number-of-month(0-28), day-of-week] update-hour(0-23) - set time for getting statistic\n" +
            "\tNumber of the month cannot be greater than 28!"+
            "\tYou can write text parameters using any case!";
    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!about"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(aboutBot))
                .then();
    }
}
