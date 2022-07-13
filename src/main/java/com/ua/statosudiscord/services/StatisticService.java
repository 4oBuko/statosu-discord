package com.ua.statosudiscord.services;

import com.ua.statosudiscord.apirequests.requests.OsuAPI;
import com.ua.statosudiscord.persistence.SequenceGeneratorService;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.persistence.repositories.StatisticRepository;
import com.ua.statosudiscord.persistence.repositories.UserRepository;
import discord4j.core.object.entity.Message;
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
            TimeUpdater.createNewUpdateTime(updated);
            updatedStatistic.add(updated);
        }
        statisticRepository.saveAll(updatedStatistic);

        return updatedStatistic;
    }


    public Statistic getLastUserStatistic(User user) {
        return statisticRepository.getStatisticByUser(user);
    }

    public Statistic updateUserStatistic(User user, int updateHour, UpdatePeriod updatePeriod) {
        Statistic statistic = osuAPI.getUserByUsername(user.getOsuUsername());
        statistic.setId(generatorService.generateSequence(Statistic.SEQUENCE_NAME));
        statistic.setUser(user);
        statistic.setPeriod(updatePeriod);
        statistic.setUpdateHour(updateHour);
        TimeUpdater.createNewUpdateTime(statistic);
        statisticRepository.save(statistic);
        return statistic;
    }
}
