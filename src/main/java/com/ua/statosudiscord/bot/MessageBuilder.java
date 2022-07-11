package com.ua.statosudiscord.bot;

import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.Statistic;

import java.time.format.DateTimeFormatter;

public class MessageBuilder {
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
    private final String ssh = "SS+: ";
    private final String updatedOn = "Updated on: ";

    public static Message createMessage(Statistic old, Statistic updated) {
        StatisticBuilder statisticBuilder = new StatisticBuilder()
                .setId(old.getId())
                .setDiscordUserId(old.getDiscordUserId())
                .setDiscordChannelId(old.getDiscordChannelId())
                .setOsuId(old.getOsuId())//can remove because I only need stats and username
                .setUsername(updated.getUsername())
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
                .setLastUpdated(updated.getLastUpdated())
                .setNextUpdateTime(updated.getNextUpdateTime())
                .setUpdateHour(updated.getUpdateHour());
        Statistic difference = statisticBuilder.build();
        String message = "";//todo:write message with difference between statistic
        return new Message(updated.getDiscordUserId(), updated.getDiscordChannelId(), message);
    }

    //    show stats when user begins to use bot
    public Message createMessage(Statistic statistic) {
        StringBuilder message = new StringBuilder();
        message.append(username).append(statistic.getUsername()).append("\n")
                .append(globalRank).append(statistic.getGlobalRank()).append("\n")
                .append(pp).append(statistic.getPp()).append("\n")
                .append(level).append(statistic.getLevel()).append("\n")
                .append(accuracy).append(statistic.getHitAccuracy()).append("\n")
                .append(playCount).append(statistic.getPlayCount()).append("\n")
                .append(playtime).append(TimeConverter.convertSecondsToString(statistic.getPlayTime())).append("\n")
                .append(a).append(statistic.getA()).append("\n")
                .append(s).append(statistic.getS()).append("\n")
                .append(ss).append(statistic.getSs()).append("\n")
                .append(sh).append(statistic.getSh()).append("\n")
                .append(ssh).append(statistic.getSsh()).append("\n")
                .append(updatedOn).append(statistic.getLastUpdated().format(DateTimeFormatter.ofPattern("LLL dd,uuuu")));
        return new Message(statistic.getDiscordUserId(), statistic.getDiscordChannelId(), message.toString());
    }
}
