package com.ua.statosudiscord.services;

import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.utils.TimeUpdater;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeUpdaterTest {

    User user = new User(null, null, null, null, UpdatePeriod.weekly, DayOfWeek.MONDAY, 0, 22);
    StatisticBuilder statisticBuilder = new StatisticBuilder()
            .setUser(user)
            .setLastUpdated(LocalDateTime.of(2022, 10, 12, 12, 12));

    Statistic statistic = statisticBuilder.build();

    @Test
    public void getNextUpdateTime() {
        assertEquals(LocalDateTime.of(2022, 10, 17, 22, 0), TimeUpdater.getNextUpdateTime(statistic));
    }
}