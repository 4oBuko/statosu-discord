package com.ua.statosudiscord.services;

import com.ua.statosudiscord.persistence.SequenceGeneratorService;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.persistence.repositories.UserRepository;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SequenceGeneratorService generatorService;

    @Autowired
    StatisticService statisticService;

    //    I can add user only with necessary ids and username, all other information
//    will be added by changeUpdateInfo
    public User addNewUser(long channelId, long userId, String username) {
        User user = new User(
                generatorService.generateSequence(User.SEQUENCE_NAME),
                channelId,
                userId,
                username,
                null,
                null,
                0,
                -1
        );
        userRepository.save(user);
        return user;
    }

    public User updateUsername(Long channelId, Long userId, String newUsername) {
        User user = userRepository.findUserByChannelIdAndUserId(channelId, userId);
        if (user != null) {
            user.setOsuUsername(newUsername);
            userRepository.save(user);
            statisticService.getNewestStatistic(user);
        }
        return user;
    }


    public User getUser(Long channelId, Long userId) {
        return userRepository.findUserByChannelIdAndUserId(channelId, userId);
    }

    public User changeUpdateInto(User user) {
        return userRepository.save(user);
    }
}
