package com.ua.statosudiscord.listeners.about;

import com.ua.statosudiscord.listeners.ProcessCommand;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class AboutListener implements ProcessCommand {
    public Mono<Void> processCommand(Message eventMessage) {
        final String aboutBot = "I'm Statosu. " +
                "I show your progress in Osu classic mode.\n" +
                "You need to give me your Osu nickname and the update time.\n" +
                "I support these commands:\n" +
                "- !about - get commands list\n" +
                "- !nickname your_nickname - set Osu nickname to get info\n" +
                "- !date frequency hour - set time for getting statistic\n" +
                "\tfrequency-how often do you want to get your stats\n" +
                "\tvalues:\n" +
                "\t\t-daily\n" +
                "\t\t-weekly\n" +
                "\t\t-montly\n" +
                "\thour - when do you want to get your stats.(value in range 0-23)";
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!about"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(aboutBot))
                .then();
    }
}
