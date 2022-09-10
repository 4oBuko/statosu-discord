package com.ua.statosudiscord.services;

import com.ua.statosudiscord.apirequests.OsuAPI;
import com.ua.statosudiscord.persistence.SequenceGeneratorService;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.persistence.repositories.StatisticRepository;
import com.ua.statosudiscord.utils.TimeUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticService {

    private Logger logger = LoggerFactory.getLogger(StatisticService.class);
    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    SequenceGeneratorService generatorService;

    @Autowired
    OsuAPI osuAPI;


    public List<Statistic> updateStatistic(LocalDateTime time) {
        List<Statistic> statisticList = statisticRepository.findAllByNextUpdateTimeIsLessThanEqualOrderById(time);
        List<Statistic> updatedStatistic = new ArrayList<>();
//      todo:rewrite using getUsers method from osuApi
        for (Statistic statistic : statisticList) {
            Statistic updated = getNewestStatistic(statistic.getUser());
            if (updated == null) {
//              return the same statistic if update failed
                updatedStatistic.add(statistic);
            } else {
                updated.setUser(statistic.getUser());
                updated.setNextUpdateTime(TimeUpdater.getNextUpdateTime(updated));
                updatedStatistic.add(updated);
            }
        }
        saveAllStatistic(updatedStatistic);
        return updatedStatistic;
    }

    public Statistic getNewestStatistic(User user) {
        ResponseEntity<Statistic> responseEntity = osuAPI.getUserByUsername(user.getOsuUsername());
        if (responseEntity == null || responseEntity.getStatusCode().isError()) {
            logger.info("Statistic wasn't updated. User username: " + user.getOsuUsername());
            return null;
        } else {
            Statistic statistic = responseEntity.getBody();
            HttpHeaders responseHeaders = responseEntity.getHeaders();
            statistic.setLastUpdated(LocalDateTime.ofEpochSecond(responseHeaders.getDate() / 1000, 0, ZoneOffset.UTC));
            return statistic;
        }
    }

    public Statistic updateUserStatistic(User user) {
        Statistic statistic = statisticRepository.getStatisticByUser(user);
        Statistic updatedStatistic = getNewestStatistic(user);
        updatedStatistic.setUser(user);
        updatedStatistic.setNextUpdateTime(TimeUpdater.getNextUpdateTime(updatedStatistic));
        return saveStatistic(updatedStatistic);
    }

    public List<Statistic> getStatisticsByNextUpdateTime(LocalDateTime time) {
        return statisticRepository.findAllByNextUpdateTimeIsLessThanEqualOrderById(time);
    }

    public Statistic saveStatistic(Statistic statistic) {
        statistic.setId(generatorService.generateSequence(Statistic.SEQUENCE_NAME));
        return statisticRepository.save(statistic);
    }

    public List<Statistic> saveAllStatistic(List<Statistic> statistics) {
        statistics = statistics.stream()
                .map(statistic -> {
                    statistic.setId(generatorService.generateSequence(Statistic.SEQUENCE_NAME));
                    return statistic;
                })
                .collect(Collectors.toList());

        return statisticRepository.saveAll(statistics);
    }
}
