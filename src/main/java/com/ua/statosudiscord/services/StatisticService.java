package com.ua.statosudiscord.services;

import com.ua.statosudiscord.apirequests.requests.OsuAPI;
import com.ua.statosudiscord.persistence.SequenceGeneratorService;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.persistence.repositories.StatisticRepository;
import com.ua.statosudiscord.persistence.repositories.UserRepository;
import com.ua.statosudiscord.utils.TimeUpdater;
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
        List<Statistic> statisticList = statisticRepository.findAllByNextUpdateTimeIsLessThanEqualOrderById(time);
        List<Statistic> updatedStatistic = new ArrayList<>();
//      todo:rewrite using getUsers method from osuApi
        for (Statistic statistic : statisticList) {
            Statistic updated = osuAPI.getUserByUsername(
                    statistic.getUser().getOsuUsername()
            );
            updated.setId(statistic.getId());
            updated.setUser(statistic.getUser());
            updated.setNextUpdateTime(TimeUpdater.getNextUpdateTime(updated));
            updatedStatistic.add(updated);
        }
        statisticRepository.saveAll(updatedStatistic);

        return updatedStatistic;
    }

    public Statistic getNewestStatistic(User user) {
        Statistic statistic = statisticRepository.getStatisticByUser(user);
        Statistic updatedStatistic = osuAPI.getUserByUsername(user.getOsuUsername());
        updatedStatistic.setId(statistic.getId());
        updatedStatistic.setUser(statistic.getUser());
        updatedStatistic.setNextUpdateTime(TimeUpdater.getNextUpdateTime(updatedStatistic));
        statisticRepository.save(updatedStatistic);
        return updatedStatistic;
    }

    public List<Statistic> getStatisticsByNextUpdateTime(LocalDateTime time) {
        return statisticRepository.findAllByNextUpdateTimeIsLessThanEqualOrderById(time);
    }
}
