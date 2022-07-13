package com.ua.statosudiscord.services;

import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;

public class TimeUpdater {
    public static void createNewUpdateTime(Statistic statistic) {
        statistic.setNextUpdateTime(statistic.getLastUpdated()
                .plusDays(statistic.getPeriod().compareTo(UpdatePeriod.daily) == 0 ? 1 : 0)
        );
        statistic.setNextUpdateTime(statistic.getLastUpdated()
                .plusWeeks(statistic.getPeriod().compareTo(UpdatePeriod.weekly) == 0 ? 1 : 0)
        );
        statistic.setNextUpdateTime(statistic.getLastUpdated()
                .plusMonths(statistic.getPeriod().compareTo(UpdatePeriod.monthly) == 0 ? 1 : 0)
        );
    }
}
