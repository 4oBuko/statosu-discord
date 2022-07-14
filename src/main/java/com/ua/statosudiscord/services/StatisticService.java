package com.ua.statosudiscord.services;

import com.ua.statosudiscord.apirequests.requests.OsuAPI;
import com.ua.statosudiscord.persistence.SequenceGeneratorService;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.persistence.repositories.StatisticRepository;
import com.ua.statosudiscord.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticService {

    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SequenceGeneratorService generatorService;

    @Autowired
    OsuAPI osuAPI;


    public List<Statistic> updateStatistic(LocalDateTime time) {
        List<Statistic> statisticList = statisticRepository.findByNextUpdateTime(time);
        List<Statistic> updatedStatistic = new ArrayList<>();
//      todo:rewrite using getUsers method from osuApi
        for (Statistic statistic : statisticList) {
            Statistic updated = osuAPI.getUserByUsername(
                    statistic.getUser().getOsuUsername()
            );
            updated.setId(statistic.getId());
            updated.setUser(statistic.getUser());
            updated.setLastUpdated(time);
            updated.setUpdateHour(statistic.getUpdateHour());
            updated.setPeriod(statistic.getPeriod());
            updated.setNextUpdateTime(statistic.getNextUpdateTime());
            updated.setNextUpdateTime(TimeUpdater.getNextUpdateTime(updated));
            updatedStatistic.add(updated);
        }
        statisticRepository.saveAll(updatedStatistic);

        return updatedStatistic;
    }


    public Statistic getLastUserStatistic(User user) {
        return statisticRepository.getStatisticByUser(user);
    }

    public Statistic addNewStatistic(User user, int updateHour, UpdatePeriod updatePeriod) {
        Statistic oldStatistic = statisticRepository.getStatisticByUser(user);
        Statistic statistic = osuAPI.getUserByUsername(user.getOsuUsername());
        if (oldStatistic == null) {

            statistic.setId(generatorService.generateSequence(Statistic.SEQUENCE_NAME));
            statistic.setUser(user);
        } else {
            statistic.setId(oldStatistic.getId());
            statistic.setUser(oldStatistic.getUser());
        }
        statistic.setPeriod(updatePeriod);
        statistic.setUpdateHour(updateHour);
        statistic.setNextUpdateTime(TimeUpdater.getNextUpdateTime(statistic));
        statisticRepository.save(statistic);
        return statistic;
    }

    public Statistic getNewestStatistic(User user) {
        Statistic statistic = statisticRepository.getStatisticByUser(user);
        Statistic updatedStatistic = osuAPI.getUserByUsername(user.getOsuUsername());
        updatedStatistic.setId(statistic.getId());
        updatedStatistic.setUser(statistic.getUser());
        updatedStatistic.setPeriod(statistic.getPeriod());
        updatedStatistic.setUpdateHour(statistic.getUpdateHour());
        updatedStatistic.setNextUpdateTime(TimeUpdater.getNextUpdateTime(updatedStatistic));
        statisticRepository.save(updatedStatistic);
        return updatedStatistic;
    }
}
