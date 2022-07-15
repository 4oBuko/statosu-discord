package com.ua.statosudiscord.services;

import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUpdater {
    public static LocalDateTime getNextUpdateTime(Statistic statistic) {
        LocalDate localDate = statistic.getLastUpdated().toLocalDate();
        LocalTime time = LocalTime.MIDNIGHT;
        time = time.plusHours(statistic.getUpdateHour());
        localDate = localDate.plusDays(
                statistic.getPeriod().compareTo(UpdatePeriod.daily) == 0 ? 1 : 0
        );
        localDate = localDate.plusWeeks(
                statistic.getPeriod().compareTo(UpdatePeriod.weekly) == 0 ? 1 : 0
        );
        localDate = localDate.plusMonths(
                statistic.getPeriod().compareTo(UpdatePeriod.monthly) == 0 ? 1 : 0
        );
        return LocalDateTime.of(localDate, time);
    }
}
