package com.ua.statosudiscord.utils;

import com.ua.statosudiscord.bot.Message;
import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.Statistic;

import java.time.format.DateTimeFormatter;

public class MessageBuilder {
    private static  final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm LLL dd,uuuu");

    public static Message createMessage(Statistic old, Statistic updated) {
        StatisticBuilder statisticBuilder = new StatisticBuilder()
                .setUser(updated.getUser())
                .setGlobalRank(old.getGlobalRank() - updated.getGlobalRank())
                .setPp(updated.getPp() - old.getPp())
                .setLevel(updated.getLevel() - old.getLevel())
                .setHitAccuracy(updated.getHitAccuracy() - old.getHitAccuracy())
                .setPlayCount(updated.getPlayCount() - old.getPlayCount())
                .setPlayTime(updated.getPlayTime() - old.getPlayTime())
                .setA(updated.getA() - old.getA())
                .setS(updated.getS() - old.getS())
                .setSs(updated.getSs() - old.getSs())
                .setSh(updated.getSh() - old.getSh())
                .setSsh(updated.getSsh() - old.getSsh())
                .setLastUpdated(old.getLastUpdated())
                .setNextUpdateTime(updated.getNextUpdateTime());
        Statistic difference = statisticBuilder.build();
        String s = """
            Previous update: %s
            Username: %s
            Global rank: %d(%+d)
            pp: %.2f(%+.2f)
            Level: %.2f(%+.2f)
            Accuracy: %.2f(%+.2f)
            Play count: %d(%+d)
            Playtime: %s(+%s)
            A: %d(%+d)
            S: %d(%+d)
            SS: %d(%+d)
            S+: %d(%+d)
            SS+: %d(%+d)
            Updated on: %s (UTC)
            Next update: %s (UTC)
            """
            .formatted(
                old.getLastUpdated().format(dateTimeFormatter),
                updated.getUser().getOsuUsername(),
                updated.getGlobalRank(),
                difference.getGlobalRank(),
                updated.getPp(),
                difference.getPp(),
                updated.getLevel(),
                difference.getLevel(),
                updated.getHitAccuracy(),
                difference.getHitAccuracy(),
                updated.getPlayCount(),
                difference.getPlayCount(),
                TimeConverter.convertSecondsToString(updated.getPlayTime()),
                TimeConverter.convertSecondsToString(difference.getPlayTime()),
                updated.getA(),
                difference.getA(),
                updated.getS(),
                difference.getS(),
                updated.getSs(),
                difference.getSs(),
                updated.getSh(),
                difference.getSh(),
                updated.getSsh(),
                difference.getSsh(),
                updated.getLastUpdated().format(dateTimeFormatter),
                updated.getNextUpdateTime().format(dateTimeFormatter)
            );
        
        return new Message(updated.getUser().getUserId(), updated.getUser().getChannelId(), s);
    }

    //    show stats when user begins to use bot
    public static Message createMessage(Statistic statistic) {
        String s = """
            Username: %s
            Global rank: %d
            pp: %.2f
            Level: %.2f
            Accuracy: %.2f
            Play count: %d
            Playtime: %s
            A: %d
            S: %d
            SS: %d
            S+: %d
            SS+: %d
            Updated on: %s (UTC)
            Next update: %s (UTC)
            """
            .formatted(
                statistic.getUser().getOsuUsername(),
                statistic.getGlobalRank(),
                statistic.getPp(),
                statistic.getLevel(),
                statistic.getHitAccuracy(),
                statistic.getPlayCount(),
                TimeConverter.convertSecondsToString(statistic.getPlayTime()),
                statistic.getA(),
                statistic.getS(),
                statistic.getSs(),
                statistic.getSh(),
                statistic.getSsh(),
                statistic.getLastUpdated().format(dateTimeFormatter),
                statistic.getNextUpdateTime().format(dateTimeFormatter)
            );
        return new Message(statistic.getUser().getUserId(), statistic.getUser().getChannelId(), s);
    }
}
