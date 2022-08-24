package com.ua.statosudiscord.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

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

    @Transient
    public static final String SEQUENCE_NAME = "statistics_sequence";
    @JsonIgnore()
    @Id
    private Long id;

    @JsonIgnore()
    @DocumentReference(lazy = true)
    User user;

    @JsonProperty("id")
    private Long osuId;

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

    private LocalDateTime lastUpdated;

    private LocalDateTime nextUpdateTime;
    @JsonProperty("statistics")
    private void unpackStatistic(Map<String, Object> statistics) {
        globalRank = (Integer) statistics.get("global_rank");
        countryRank = (Integer) statistics.get("country_rank");
        pp = (Double) statistics.get("pp");
        Map<String, Object> level = (Map<String, Object>) statistics.get("level");
        this.level = (Integer) level.get("current") + ((Integer) level.get("progress") / 100.0);
        hitAccuracy = (Double) statistics.get("hit_accuracy");
        playTime = (Integer) statistics.get("play_time");
        playCount = (Integer) statistics.get("play_count");
        Map<String, Object> gradeCounts = (Map<String, Object>) statistics.get("grade_counts");
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
        return id.equals(statistic.id) && user.equals(statistic.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user);
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "id=" + id +
                ", discordUserId=" + user.getUserId() +
                ", discordChannelId=" + user.getChannelId() +
                ", osuId=" + osuId +
                ", username='" + user.getOsuUsername() + '\'' +
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
                ", lastUpdated=" + lastUpdated +
                ", nextUpdateTime=" + nextUpdateTime +
                '}';
    }
}
