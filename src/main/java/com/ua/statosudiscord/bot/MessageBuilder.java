package com.ua.statosudiscord.bot;

import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.Statistic;

import java.time.format.DateTimeFormatter;

public class MessageBuilder {
    //    todo:separate builders (for example add error messages etc.)
    private static final String previousUpdate = "Previous update: ";
    private static final String username = "Username: ";
    private static final String globalRank = "Global rank: ";
    private static final String pp = "pp: ";
    private static final String level = "Level: ";
    private static final String accuracy = "Accuracy: ";
    private static final String playCount = "Play count: ";
    private static final String playtime = "Playtime: ";
    private static final String a = "A: ";
    private static final String s = "S: ";
    private static final String ss = "SS: ";
    private static final String sh = "S+: ";
    private static final String ssh = "SS+: ";
    private static final String updatedOn = "Updated on: ";

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
                .setPeriod(updated.getPeriod())
                .setLastUpdated(old.getLastUpdated())
                .setNextUpdateTime(updated.getNextUpdateTime())
                .setUpdateHour(updated.getUpdateHour());
        Statistic difference = statisticBuilder.build();
        StringBuilder message = new StringBuilder();
        message.append(previousUpdate)
                .append(old.getLastUpdated().format(DateTimeFormatter.ofPattern("HH:mm LLL dd,uuuu")))
                .append("\n");
        message.append(username)
                .append(updated.getUser().getOsuUsername())
                .append("\n");
        message.append(globalRank)
                .append(updated.getGlobalRank())
                .append("(")
                .append(String.format("%+d", difference.getGlobalRank()))
                .append(")")
                .append("\n");
        message.append(pp)
                .append(updated.getPp())
                .append("(")
                .append(String.format("%+.2f", difference.getPp()))
                .append(")")
                .append("\n");
        message.append(level)
                .append(updated.getLevel())
                .append("(")
                .append(String.format("%+.2f", difference.getLevel()))
                .append(")")
                .append("\n");
        message.append(accuracy)
                .append(updated.getHitAccuracy())
                .append("(")
                .append(String.format("%+.2f", difference.getHitAccuracy()))
                .append(")")
                .append("\n");
        message.append(playCount)
                .append(updated.getPlayCount())
                .append("(")
                .append("+")
                .append(difference.getPlayCount())
                .append(")")
                .append("\n");
        message.append(playtime)
                .append(TimeConverter.convertSecondsToString(updated.getPlayTime()))
                .append("(")
                .append("+")
                .append(TimeConverter.convertSecondsToString(difference.getPlayTime()))
                .append(")")
                .append("\n");
        message.append(a)
                .append(updated.getA())
                .append("(")
                .append(String.format("%+d", difference.getA()))
                .append(")")
                .append("\n");
        message.append(s)
                .append(updated.getS())
                .append("(")
                .append(String.format("%+d", difference.getS()))
                .append(")")
                .append("\n");
        message.append(ss)
                .append(updated.getSs())
                .append("(")
                .append(String.format("%+d", difference.getSs()))
                .append(")")
                .append("\n");
        message.append(sh)
                .append(updated.getSh())
                .append("(")
                .append(String.format("%+d", difference.getSh()))
                .append(")")
                .append("\n");
        message.append(ssh)
                .append(updated.getSsh())
                .append("(")
                .append(String.format("%+d", difference.getSsh()))
                .append(")")
                .append("\n");
        message.append(updatedOn)
                .append(updated.getLastUpdated().format(DateTimeFormatter.ofPattern("HH:mm LLL dd,uuuu")));
        return new Message(updated.getUser().getUserId(), updated.getUser().getChannelId(), message.toString());
    }

    //    show stats when user begins to use bot
    public static Message createMessage(Statistic statistic) {
        StringBuilder message = new StringBuilder();
        message.append(username)
                .append(statistic.getUser().getOsuUsername())
                .append("\n");
        message.append(globalRank)
                .append(statistic.getGlobalRank())
                .append("\n");
        message.append(pp)
                .append(statistic.getPp())
                .append("\n");
        message.append(level)
                .append(statistic.getLevel())
                .append("\n");
        message.append(accuracy)
                .append(statistic.getHitAccuracy())
                .append("\n");
        message.append(playCount)
                .append(statistic.getPlayCount())
                .append("\n");
        message.append(playtime)
                .append(TimeConverter.convertSecondsToString(statistic.getPlayTime()))
                .append("\n");
        message.append(a)
                .append(statistic.getA())
                .append("\n");
        message.append(s)
                .append(statistic.getS())
                .append("\n");
        message.append(ss)
                .append(statistic.getSs())
                .append("\n");
        message.append(sh)
                .append(statistic.getSh())
                .append("\n");
        message.append(ssh)
                .append(statistic.getSsh())
                .append("\n");
        message.append(updatedOn)
                .append(statistic.getLastUpdated().format(DateTimeFormatter.ofPattern("LLL dd,uuuu")));
        return new Message(statistic.getUser().getUserId(), statistic.getUser().getChannelId(), message.toString());
    }
}
