package com.ua.statosudiscord.persistence.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;


@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistic implements Serializable {
    @Id
    private Long discordUserId;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
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

    @JsonProperty("statistics")
    private void unpackStatistic(Map<String, Object> statistics) {
        System.out.println(statistics);
        globalRank = (Integer) statistics.get("global_rank");
        countryRank = (Integer) statistics.get("country_rank");
        pp = (Double) statistics.get("pp");
        Map<String, Object> level = (Map<String, Object>) statistics.get("level");
        System.out.println(level);
        this.level = (Integer) level.get("current") + ((Integer) level.get("progress") / 100.0);
        hitAccuracy = (Double) statistics.get("hit_accuracy");
        playTime = (Integer) statistics.get("play_time");
        playCount = (Integer) statistics.get("play_count");
        Map<String, Object> gradeCounts = (Map<String, Object>) statistics.get("grade_counts");
        System.out.println(gradeCounts);
        a = (Integer) gradeCounts.get("a");
        s = (Integer) gradeCounts.get("s");
        ss = (Integer) gradeCounts.get("ss");
        sh = (Integer) gradeCounts.get("sh");
        ssh = (Integer) gradeCounts.get("ssh");
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistic)) return false;
        Statistic statistic = (Statistic) o;
        return discordUserId.equals(statistic.discordUserId) && id.equals(statistic.id) && username.equals(statistic.username) && globalRank.equals(statistic.globalRank) && countryRank.equals(statistic.countryRank) && pp.equals(statistic.pp) && level.equals(statistic.level) && hitAccuracy.equals(statistic.hitAccuracy) && playTime.equals(statistic.playTime) && playCount.equals(statistic.playCount) && a.equals(statistic.a) && s.equals(statistic.s) && ss.equals(statistic.ss) && sh.equals(statistic.sh) && ssh.equals(statistic.ssh) && period == statistic.period && lastUpdated.equals(statistic.lastUpdated) && nextUpdateTime.equals(statistic.nextUpdateTime) && updateHour.equals(statistic.updateHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discordUserId, id, username, globalRank, countryRank, pp, level, hitAccuracy, playTime, playCount, a, s, ss, sh, ssh, period, lastUpdated, nextUpdateTime, updateHour);
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "discordUserId=" + discordUserId +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", globalRank=" + globalRank +
                ", countryRank=" + countryRank +
                ", pp=" + pp +
                ", level=" + level +
                ", hitAccuracy=" + hitAccuracy +
                ", playTime=" + playTime +
                ", playCount=" + playCount +
                ", a=" + a +
                ", s=" + s +
                ", ss=" + ss +
                ", sh=" + sh +
                ", ssh=" + ssh +
                ", period=" + period +
                ", lastUpdated=" + lastUpdated +
                ", nextUpdateTime=" + nextUpdateTime +
                ", updateHour=" + updateHour +
                '}';
    }
}
