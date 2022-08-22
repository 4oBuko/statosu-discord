package com.ua.statosudiscord.services;

import com.ua.statosudiscord.persistence.SequenceGeneratorService;
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

    public User addNewUser(Message message, String username, UpdatePeriod updatePeriod, DayOfWeek dayOfWeek, int dayOfMonth, int updateTime) {
        User user = new User(
                generatorService.generateSequence(User.SEQUENCE_NAME),
                message.getChannelId().asLong(),
                message.getUserData().id().asLong(),
                username,
                updatePeriod,
                dayOfWeek,
                dayOfMonth,
                updateTime
        );
        userRepository.save(user);
        return user;
    }

    //    todo: change message to user id and user id
    public User updateUsername(Message message, String newUsername) {
        User user = userRepository.findUserByChannelIdAndUserId(message.getChannelId().asLong(), message.getUserData().id().asLong());
        if (user != null) {
            user.setOsuUsername(newUsername);
            userRepository.save(user);
            statisticService.getNewestStatistic(user);
        }
        return user;
    }

    public User getUser(Message message) {
        return userRepository.findUserByChannelIdAndUserId(message.getChannelId().asLong(), message.getUserData().id().asLong());
    }
}
