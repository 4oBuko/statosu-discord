package com.ua.statosudiscord.services;

import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeUpdaterTest {

    StatisticBuilder statisticBuilder = new StatisticBuilder()
            .setUpdateHour(22)
            .setPeriod(UpdatePeriod.weekly)
            .setLastUpdated(LocalDateTime.of(2022,10,12,12,12));

    Statistic statistic = statisticBuilder.build();

    @Test
    public void getNextUpdateTime() {
        assertEquals(LocalDateTime.of(2022,10,19,22, 0),TimeUpdater.getNextUpdateTime(statistic));
    }
}