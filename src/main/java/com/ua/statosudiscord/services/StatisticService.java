package com.ua.statosudiscord.services;

import com.ua.statosudiscord.apirequests.OsuAPI;
import com.ua.statosudiscord.persistence.SequenceGeneratorService;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.persistence.repositories.StatisticRepository;
import com.ua.statosudiscord.utils.StatisticJSONParser;
import com.ua.statosudiscord.utils.TimeUpdater;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedList;
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
                updated.setId(statistic.getId());
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
        updatedStatistic.setId(statistic.getId());
        updatedStatistic.setUser(user);
        updatedStatistic.setNextUpdateTime(TimeUpdater.getNextUpdateTime(updatedStatistic));
        return saveStatistic(updatedStatistic);
    }

    public List<Statistic> getStatisticsByNextUpdateTime(LocalDateTime time) {
        return statisticRepository.findAllByNextUpdateTimeIsLessThanEqualOrderById(time);
    }

    public Statistic saveStatistic(Statistic statistic) {
        if (statistic.getId() == null) {
            statistic.setId(generatorService.generateSequence(Statistic.SEQUENCE_NAME));
        }
        return statisticRepository.save(statistic);
    }

    public List<Statistic> saveAllStatistic(List<Statistic> statistics) {
        statistics = statistics.stream()
                .peek(statistic -> {
                    if (statistic.getId() == null)
                        statistic.setId(generatorService.generateSequence(Statistic.SEQUENCE_NAME));
                })
                .collect(Collectors.toList());

        return statisticRepository.saveAll(statistics);
    }

    public List<Statistic> updateStatisticByTime(LocalDateTime time) {
//        todo: test method
        List<Statistic> statisticToUpdate = statisticRepository.findAllByNextUpdateTimeIsLessThanEqualOrderById(time);
        List<Statistic> updatedStatistic = new LinkedList<>();
//        update max 50 elements per request and add them into updatedStatistic list
        for (int i = 0; i < statisticToUpdate.size(); i += 50) {
            ResponseEntity<String> response;
            int maxIndex = (statisticToUpdate.size() - i >= 50) ? i + 50 : statisticToUpdate.size();
            response = osuAPI.getMultipleUsers(statisticToUpdate.subList(i, maxIndex).stream().map(Statistic::getOsuId).toList());
            if (response == null) {
//                if response is null set old statistic
                for (int j = i; j < maxIndex; j++) {
                    updatedStatistic.add(statisticToUpdate.get(j));
                }
            } else {
                List<Statistic> updatedPart = StatisticJSONParser.parseStatistic(response.getBody());
                HttpHeaders responseHeaders = response.getHeaders();
                updatedPart = updatedPart.stream().peek(x -> x.setLastUpdated(LocalDateTime.ofEpochSecond(responseHeaders.getDate() / 1000, 0, ZoneOffset.UTC))).collect(Collectors.toList());
                for (int j = i; j < maxIndex; j++) {
                    Statistic newStatistic = updatedPart.get(j - i);
                    Statistic oldStatistic = statisticToUpdate.get(j);
                    newStatistic.setId(oldStatistic.getId());
                    newStatistic.setUser(oldStatistic.getUser());
                    newStatistic.setNextUpdateTime(TimeUpdater.getNextUpdateTime(newStatistic));
                    updatedStatistic.add(newStatistic);
                }
                saveAllStatistic(updatedStatistic);
            }
        }
        return updatedStatistic;
    }
}
