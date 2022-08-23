package com.ua.statosudiscord.utils;

import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUpdater {
    public static LocalDateTime getNextUpdateTime(Statistic statistic) {
        LocalDate lastUpdated = statistic.getLastUpdated().toLocalDate();
        LocalTime time = LocalTime.MIDNIGHT;
        time = time.plusHours(statistic.getUser().getUpdateTime());
        if (statistic.getLastUpdated().toLocalDate().equals(LocalDate.now()) &&
                statistic.getUser().getUpdateTime() - statistic.getLastUpdated().getHour() >= 2) {
            return LocalDateTime.of(LocalDate.now(), time);
        }
        if (statistic.getUser().getUpdatePeriod().equals(UpdatePeriod.daily)) {
            lastUpdated = lastUpdated.plusDays(1);
        } else if (statistic.getUser().getUpdatePeriod().equals(UpdatePeriod.weekly)) {
            DayOfWeek lastUpdatedDay = statistic.getLastUpdated().getDayOfWeek();
            DayOfWeek updateDay = statistic.getUser().getDayOfWeek();
            int daysToNextUpdate;
//           find number of days to the next update
            if (updateDay.compareTo(lastUpdatedDay) > 0) {
                daysToNextUpdate = updateDay.getValue() - lastUpdatedDay.getValue();
            } else {
                daysToNextUpdate = 7 - (lastUpdatedDay.getValue() - updateDay.getValue());
            }
            lastUpdated = lastUpdated.plusDays(daysToNextUpdate);
        } else if (statistic.getUser().getUpdatePeriod().equals(UpdatePeriod.monthly)) {
            if (statistic.getNextUpdateTime() != null && statistic.getNextUpdateTime().getMonth() == statistic.getLastUpdated().getMonth() &&
                    statistic.getUser().getDayOfMonth() > statistic.getLastUpdated().getDayOfMonth()
            ) {
                lastUpdated = lastUpdated.plusDays(statistic.getUser().getDayOfMonth() - statistic.getLastUpdated().getDayOfMonth());
            } else {
                if (lastUpdated.getMonth().getValue() == 12) {
                    lastUpdated = LocalDate.of(LocalDate.now().getYear() + 1, 1, statistic.getUser().getDayOfMonth());
                } else {
                    lastUpdated = LocalDate.of(LocalDate.now().getYear(), statistic.getLastUpdated().getMonth().getValue() + 1, statistic.getUser().getDayOfMonth());
                }
            }
        }
        return LocalDateTime.of(lastUpdated, time);
    }
}
