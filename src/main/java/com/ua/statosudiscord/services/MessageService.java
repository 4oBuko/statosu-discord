package com.ua.statosudiscord.services;

import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.utils.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    StatisticService statisticService;

    @Autowired
    UserService userService;

    public String getNewStatistic(User user) {
        Statistic newestStatistic = statisticService.updateUserStatistic(user);
        if (newestStatistic == null) {
            return "Error.Something went wrong";
        } else {
            return MessageBuilder.createMessage(newestStatistic).getMessage();
        }
    }

    public String updateUsername(Long channelId, Long userId, String newUsername) {
        User userFromDB = userService.getUser(channelId, userId);
        Statistic statisticForNewNickname = statisticService.getNewestStatistic(new User(channelId, userId, newUsername));
        if (statisticForNewNickname == null) {
            return "Error. This username doesn't exist";
        }
        else if (userFromDB == null) {
            User newUser = userService.addNewUser(channelId, userId, newUsername);
            return "Welcome. Your osu username is " + newUser.getOsuUsername();
        }
        else if (newUsername.equals(userFromDB.getOsuUsername())) {
            return newUsername + " is already your username";
        } else {
            String oldUsername = userFromDB.getOsuUsername();
            User updatedUser = userService.updateUsername(channelId, userId, newUsername);
            return "Old username: " + oldUsername + "\n new username: " + updatedUser.getOsuUsername();
        }
    }
}
