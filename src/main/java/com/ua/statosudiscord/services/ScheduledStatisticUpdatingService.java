package com.ua.statosudiscord.services;

import com.ua.statosudiscord.bot.Message;
import com.ua.statosudiscord.utils.MessageBuilder;
import com.ua.statosudiscord.bot.MessageSender;
import com.ua.statosudiscord.persistence.entities.Statistic;
import discord4j.core.GatewayDiscordClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
@AllArgsConstructor
public class ScheduledStatisticUpdatingService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledStatisticUpdatingService.class);
    private StatisticService statisticService;

    MessageSender messageSender;

    private GatewayDiscordClient gatewayDiscordClient;

    @Scheduled(cron = "0 0 * * * *")//check for updates every hour
    public void updateStatisticAndSend() {
        LocalDateTime updateTime = LocalDateTime.of(
                LocalDate.now(), LocalTime.MIDNIGHT.plusHours(LocalDateTime.now().getHour()
                )
        );
        logger.warn(String.valueOf(gatewayDiscordClient.rest().getApplicationId().block()));
        logger.debug("Current time: " + updateTime);
        List<Statistic> oldStatistic = statisticService.getStatisticsByNextUpdateTime(updateTime);
        List<Statistic> updatedStatistic = statisticService.updateStatisticByTime(updateTime);
        logger.debug("Statistic was updated");
        for (int i = 0; i < updatedStatistic.size(); i++) {
            Message message = MessageBuilder.createMessage(oldStatistic.get(i), updatedStatistic.get(i));
            if (oldStatistic.get(i).equals(updatedStatistic.get(i))) {
                message.setMessage("Failed to update statistic. Last updated statistic:" + message.getMessage());
            }
            messageSender.sendMessageInChannelWithoutMentioningUser(message);
            messageSender.sendMessageInChannelWithUserMention(message);
        }
        logger.debug("Message were sent");
    }
}
