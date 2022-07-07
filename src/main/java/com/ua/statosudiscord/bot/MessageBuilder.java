package com.ua.statosudiscord.bot;

import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.Statistic;

public class MessageBuilder {
    public static Message createMessage(Statistic old, Statistic updated) {
        StatisticBuilder statisticBuilder = new StatisticBuilder()
                .setId(old.getId())
                .setDiscordUserId(old.getDiscordUserId())
                .setDiscordChannelId(old.getDiscordChannelId())
                .setOsuId(old.getOsuId())
                .setUsername(updated.getUsername())
                .setGlobalRank(old.getGlobalRank() - updated.getGlobalRank())
                .setPp(updated.getPp() - old.getPp())
                .setLevel(updated.getLevel() - old.getLevel())
                .setHitAccuracy(updated.getHitAccuracy() - old.getHitAccuracy())
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
        String message = "";//todo:write message with difference between statistic
        return new Message(statistic.getDiscordUserId(), statistic.getDiscordChannelId(), message);

    }
}
