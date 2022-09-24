package com.ua.statosudiscord.services;

import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.utils.TimeUpdater;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeUpdaterTest {

    final User monthlyUpdateUser = new User(null, null, null, null, UpdatePeriod.monthly, null, 15, 20);

    final User weeklyUpdateUser = new User(null, null, null, null, UpdatePeriod.weekly, DayOfWeek.MONDAY, 0, 18);

    final User dailyUpdateUser = new User(null, null, null, null, UpdatePeriod.daily, null, 0, 22);

    final StatisticBuilder dailyUpdatedStatisticBuilder = new StatisticBuilder().setUser(dailyUpdateUser);

    final StatisticBuilder weeklyUpdatedStatisticBuilder = new StatisticBuilder().setUser(weeklyUpdateUser);

    final StatisticBuilder monthlyUpdatedStatisticBuilder = new StatisticBuilder().setUser(monthlyUpdateUser);

    @Test
    public void dailyUpdate() {
        dailyUpdatedStatisticBuilder.setLastUpdated(LocalDateTime.of(LocalDate.now().getYear(), 11, 14, 23, 30));
        assertEquals(LocalDateTime.of(LocalDate.now().getYear(), 11, 15, dailyUpdateUser.getUpdateTime(), 0), TimeUpdater.getNextUpdateTime(dailyUpdatedStatisticBuilder.build()));
    }

    @Test
    public void weeklyUpdate() {
        weeklyUpdatedStatisticBuilder.setLastUpdated(LocalDateTime.of(LocalDate.now().getYear(), 10, 10, 10, 0));
        assertEquals(LocalDateTime.of(LocalDate.now().getYear(), 10, 17, 18, 0), TimeUpdater.getNextUpdateTime(weeklyUpdatedStatisticBuilder.build()));
    }

    @Test
    public void weeklyUpdateWithPreviousUpdateInOtherDay() {
        weeklyUpdatedStatisticBuilder.setLastUpdated(LocalDateTime.of(LocalDate.now().getYear(), 10, 16, 10, 0));
        assertEquals(LocalDateTime.of(LocalDate.now().getYear(), 10, 17, 18, 0), TimeUpdater.getNextUpdateTime(weeklyUpdatedStatisticBuilder.build()));
    }

    @Test
    public void weeklyUpdateInNextYear() {
        weeklyUpdatedStatisticBuilder.setLastUpdated(LocalDateTime.of(2022, 12, 26, 18, 0));
        assertEquals(LocalDateTime.of(2023, 1, 2, 18, 0), TimeUpdater.getNextUpdateTime(weeklyUpdatedStatisticBuilder.build()));
    }

    @Test
    public void monthlyUpdate() {
        monthlyUpdatedStatisticBuilder.setLastUpdated(LocalDateTime.of(LocalDate.now().getYear(), 10, 15, 18, 15));
        assertEquals(LocalDateTime.of(LocalDate.now().getYear(), 11, 15, monthlyUpdateUser.getUpdateTime(), 0), TimeUpdater.getNextUpdateTime(monthlyUpdatedStatisticBuilder.build()));
    }

    @Test
    public void monthlyUpdateNextYear() {
        monthlyUpdatedStatisticBuilder.setLastUpdated(LocalDateTime.of(LocalDate.now().getYear(), 12, 14, 18, 15));
        assertEquals(LocalDateTime.of(LocalDate.now().getYear() + 1, 1, 15, monthlyUpdateUser.getUpdateTime(), 0), TimeUpdater.getNextUpdateTime(monthlyUpdatedStatisticBuilder.build()));

    }

    @Test
    public void monthlyUpdateWithPreviousUpdateInOtherDay() {
        monthlyUpdatedStatisticBuilder.setLastUpdated(LocalDateTime.of(LocalDate.now().getYear(), 10, 18, 18, 15));
        assertEquals(LocalDateTime.of(LocalDate.now().getYear(), 11, 15, monthlyUpdateUser.getUpdateTime(), 0), TimeUpdater.getNextUpdateTime(monthlyUpdatedStatisticBuilder.build()));
    }
}