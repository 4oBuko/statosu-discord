package com.ua.statosudiscord.services;

import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;

public class TimeUpdater {
    public static void createNewUpdateTime(Statistic statistic) {
        statistic.setNextUpdateTime(statistic.getNextUpdateTime()
                .plusDays(statistic.getPeriod().compareTo(UpdatePeriod.daily) == 0 ? 1 : 0)
        );
        statistic.setNextUpdateTime(statistic.getNextUpdateTime()
                .plusWeeks(statistic.getPeriod().compareTo(UpdatePeriod.weekly) == 0 ? 1 : 0)
        );
        statistic.setNextUpdateTime(statistic.getNextUpdateTime()
                .plusMonths(statistic.getPeriod().compareTo(UpdatePeriod.monthly) == 0 ? 1 : 0)
        );
    }
}
