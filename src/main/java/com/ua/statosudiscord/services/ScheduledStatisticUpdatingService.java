package com.ua.statosudiscord.services;

import com.ua.statosudiscord.bot.Message;
import com.ua.statosudiscord.utils.MessageBuilder;
import com.ua.statosudiscord.bot.MessageSender;
import com.ua.statosudiscord.persistence.entities.Statistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
public class ScheduledStatisticUpdatingService {

    Logger logger = LoggerFactory.getLogger(ScheduledStatisticUpdatingService.class);
    @Autowired
    private StatisticService statisticService;

    @Autowired
    MessageSender messageSender;

    @Scheduled(cron = "0 0 * * * *")//check for updates every hour
    public void updateStatisticAndSend() {
        LocalDateTime updateTime = LocalDateTime.of(
                LocalDate.now(), LocalTime.MIDNIGHT.plusHours(LocalDateTime.now().getHour()
                )
        );
        logger.debug("Current time: " + updateTime);
        List<Statistic> oldStatistic = statisticService.getStatisticsByNextUpdateTime(updateTime);
        List<Statistic> updatedStatistic = statisticService.updateStatistic(updateTime);
        logger.debug("Statistic was updated");
        for (int i = 0; i < updatedStatistic.size(); i++) {
            Message message = MessageBuilder.createMessage(oldStatistic.get(i), updatedStatistic.get(i));
            if (oldStatistic.get(i).equals(updatedStatistic.get(i))) {
                message.setMessage("Failed to update statistic. Last updated statistic:" + message.getMessage());
            }
            messageSender.sendMessageInChannelWithUserMention(message);
        }
        logger.debug("Message were sent");
    }
}
