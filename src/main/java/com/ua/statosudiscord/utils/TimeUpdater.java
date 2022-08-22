package com.ua.statosudiscord.utils;

import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUpdater {
    public static LocalDateTime getNextUpdateTime(Statistic statistic) {
//      todo: check if I need a value for local date
//      todo: check if difference between previous update and planned update
//         (because system wasn't working) is more than 1 or 2 hours
//        if more, two updates in one day
        if (statistic.getNextUpdateTime() != null &&
                statistic.getLastUpdated().toLocalDate().equals(statistic.getNextUpdateTime().toLocalDate()) &&
                statistic.getNextUpdateTime().getHour() - statistic.getLastUpdated().getHour() >= 2) {
            return statistic.getNextUpdateTime();
        }
        LocalDate lastUpdated = statistic.getLastUpdated().toLocalDate();
        LocalTime time = LocalTime.MIDNIGHT;
        time = time.plusHours(statistic.getUser().getUpdateTime());
        if (statistic.getUser().getUpdatePeriod().equals(UpdatePeriod.daily)) {
            lastUpdated = lastUpdated.plusDays(1);
        } else if (statistic.getUser().getUpdatePeriod().equals(UpdatePeriod.weekly)) {
            DayOfWeek lastUpdatedDay = statistic.getLastUpdated().getDayOfWeek();
            DayOfWeek updateDay = statistic.getUser().getDayOfWeek();
            int daysToNextUpdate;
//           find number of days to the next update
            if (updateDay.compareTo(lastUpdatedDay) >= 0) {
                daysToNextUpdate = updateDay.getValue() - lastUpdatedDay.getValue();
            } else {
                daysToNextUpdate = 7 - (lastUpdatedDay.getValue() - updateDay.getValue());
            }
            lastUpdated = lastUpdated.plusDays(daysToNextUpdate);
        } else if (statistic.getUser().getUpdatePeriod().equals(UpdatePeriod.monthly)) {
            if (statistic.getNextUpdateTime().getMonth() == statistic.getLastUpdated().getMonth() &&
                    statistic.getUser().getDayOfMonth() > statistic.getLastUpdated().getDayOfMonth()
            ) {
                lastUpdated = lastUpdated.plusDays(statistic.getUser().getDayOfMonth() - statistic.getLastUpdated().getDayOfMonth());
            } else {
                if (lastUpdated.getMonth().getValue() == 12) {
                    lastUpdated = LocalDate.of(LocalDate.now().getYear() + 1, 1, statistic.getUser().getDayOfMonth());
                } else {
                    lastUpdated = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth().getValue() + 1, statistic.getUser().getDayOfMonth());
                }
            }
//            if the number of the month is bigger than previous update time (15 and 12)
//            just add days

//            if the previous update day is less than update day and the month is the same
//            add new month to the date

//                day can be less than next update
        }
        return LocalDateTime.of(lastUpdated, time);
    }
}
