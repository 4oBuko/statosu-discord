package com.ua.statosudiscord.persistence.entities;

import com.ua.statosudiscord.UpdatePeriod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
    private long discordUserId;

    private long osuId;

    private String nickname;

    private int rank;

    private int countryRank;

    private double pp;

    private double level;

    private double accuracy;

    private long playTime;

    private int aRank;

    private int sRank;

    private int ssRank;

    private int shRank;

    private int sshRank;

    private UpdatePeriod period;

    private LocalDateTime lastUpdated;

    private LocalDateTime nextUpdateTime;

    private int updateHour;//todo:add limit 0-23


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistic)) return false;
        Statistic statistic = (Statistic) o;
        return discordUserId == statistic.discordUserId && osuId == statistic.osuId && rank == statistic.rank && countryRank == statistic.countryRank && Double.compare(statistic.pp, pp) == 0 && Double.compare(statistic.level, level) == 0 && Double.compare(statistic.accuracy, accuracy) == 0 && playTime == statistic.playTime && aRank == statistic.aRank && sRank == statistic.sRank && ssRank == statistic.ssRank && shRank == statistic.shRank && sshRank == statistic.sshRank && updateHour == statistic.updateHour && Objects.equals(nickname, statistic.nickname) && period == statistic.period && Objects.equals(lastUpdated, statistic.lastUpdated) && Objects.equals(nextUpdateTime, statistic.nextUpdateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discordUserId, osuId, nickname, rank, countryRank, pp, level, accuracy, playTime, aRank, sRank, ssRank, shRank, sshRank, period, lastUpdated, nextUpdateTime, updateHour);
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "discordUserId=" + discordUserId +
                ", osuId=" + osuId +
                ", nickname='" + nickname + '\'' +
                ", rank=" + rank +
                ", countryRank=" + countryRank +
                ", pp=" + pp +
                ", level=" + level +
                ", accuracy=" + accuracy +
                ", playTime=" + playTime +
                ", aRank=" + aRank +
                ", sRank=" + sRank +
                ", ssRank=" + ssRank +
                ", shRank=" + shRank +
                ", sshRank=" + sshRank +
                ", period=" + period +
                ", lastUpdated=" + lastUpdated +
                ", nextUpdateTime=" + nextUpdateTime +
                ", updateHour=" + updateHour +
                '}';
    }
}
