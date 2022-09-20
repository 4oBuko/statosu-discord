package com.ua.statosudiscord.services;

import com.ua.statosudiscord.persistence.SequenceGeneratorService;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.persistence.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private SequenceGeneratorService generatorService;

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
