package com.ua.statosudiscord.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;
import java.util.Objects;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private Long id;
    private Long channelId;
    private Long userId;
    private String osuUsername;

    private UpdatePeriod updatePeriod;

    private DayOfWeek dayOfWeek;

    private Integer dayOfMonth;

    private Integer updateTime;


    public User(Long channelId, Long userId, String osuUsername) {
        this.channelId = channelId;
        this.userId = userId;
        this.osuUsername = osuUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id.equals(user.id) && channelId.equals(user.channelId) && userId.equals(user.userId) && osuUsername.equals(user.osuUsername) && updatePeriod == user.updatePeriod && dayOfWeek == user.dayOfWeek && Objects.equals(dayOfMonth, user.dayOfMonth) && updateTime.equals(user.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, userId, osuUsername, updatePeriod, dayOfWeek, dayOfMonth, updateTime);
    }
}
