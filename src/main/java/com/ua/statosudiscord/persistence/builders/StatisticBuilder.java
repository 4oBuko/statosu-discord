package com.ua.statosudiscord.persistence.builders;

import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import com.ua.statosudiscord.persistence.entities.Statistic;

import java.time.LocalDateTime;

public class StatisticBuilder {
    private Long discordUserId;

    private Long id;

    private String username;

    private Integer globalRank;

    private Integer countryRank;

    private Double pp;

    private Double level;

    private Double hitAccuracy;

    private Integer playTime;

    private Integer playCount;

    private Integer a;

    private Integer s;

    private Integer ss;

    private Integer sh;

    private Integer ssh;

    private UpdatePeriod period;

    private LocalDateTime lastUpdated;

    private LocalDateTime nextUpdateTime;
    private Integer updateHour;

    public StatisticBuilder setDiscordUserId(Long discordUserId) {
        this.discordUserId = discordUserId;
        return this;
    }

    public StatisticBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public StatisticBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public StatisticBuilder setGlobalRank(Integer globalRank) {
        this.globalRank = globalRank;
        return this;
    }

    public StatisticBuilder setCountryRank(Integer countryRank) {
        this.countryRank = countryRank;
        return this;
    }

    public StatisticBuilder setPp(Double pp) {
        this.pp = pp;
        return this;
    }

    public StatisticBuilder setLevel(Double level) {
        this.level = level;
        return this;
    }

    public StatisticBuilder setHitAccuracy(Double hitAccuracy) {
        this.hitAccuracy = hitAccuracy;
        return this;
    }

    public StatisticBuilder setPlayTime(Integer playTime) {
        this.playTime = playTime;
        return this;
    }

    public StatisticBuilder setPlayCount(Integer playCount) {
        this.playCount = playCount;
        return this;
    }

    public StatisticBuilder setA(Integer a) {
        this.a = a;
        return this;
    }

    public StatisticBuilder setS(Integer s) {
        this.s = s;
        return this;
    }

    public StatisticBuilder setSs(Integer ss) {
        this.ss = ss;
        return this;
    }

    public StatisticBuilder setSh(Integer sh) {
        this.sh = sh;
        return this;
    }

    public StatisticBuilder setSsh(Integer ssh) {
        this.ssh = ssh;
        return this;
    }

    public StatisticBuilder setPeriod(UpdatePeriod period) {
        this.period = period;
        return this;
    }

    public StatisticBuilder setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public StatisticBuilder setNextUpdateTime(LocalDateTime nextUpdateTime) {
        this.nextUpdateTime = nextUpdateTime;
        return this;
    }

    public StatisticBuilder setUpdateHour(Integer updateHour) {
        this.updateHour = updateHour;
        return this;
    }

    public Statistic build() {
        return new Statistic(discordUserId, id, username, globalRank, countryRank, pp, level, hitAccuracy, playTime, playCount, a, s, ss, sh, ssh, period, lastUpdated, nextUpdateTime, updateHour);
    }
}
