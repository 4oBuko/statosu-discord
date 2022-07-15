package com.ua.statosudiscord.services;

import com.ua.statosudiscord.bot.Message;
import com.ua.statosudiscord.bot.MessageBuilder;
import com.ua.statosudiscord.bot.MessageSender;
import com.ua.statosudiscord.persistence.entities.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
public class ScheduledStatisticUpdatingService {

    @Autowired
    private StatisticService statisticService;

    @Autowired
    MessageSender messageSender;

    //    @Scheduled(cron = "0 0 0 * * *")//check for updates every hour
    @Scheduled(cron = "0 */5 * * * *")//test every 10 minutes
    public void updateStatisticAndSend() {
        System.out.println("Event happened" + LocalDateTime.now());
        LocalDateTime updateTime = LocalDateTime.of(
                LocalDate.now(), LocalTime.MIDNIGHT.plusHours(LocalDateTime.now().getHour()
                )
        );

        System.out.println(updateTime);
        List<Statistic> oldStatistic = statisticService.getStatisticsByNextUpdateTime(updateTime);
        List<Statistic> updatedStatistic = statisticService.updateStatistic(updateTime);
        System.out.println(oldStatistic.size());
        System.out.println(updatedStatistic.size());

        for(int i = 0; i < updatedStatistic.size(); i++) {
            System.out.println("Old statistic:\n" + oldStatistic.get(i));
            System.out.println("Updated statistic:\n " + updatedStatistic.get(i));
            if(oldStatistic.get(i).getId().equals(updatedStatistic.get(i).getId())) {
                Message message = MessageBuilder.createMessage(oldStatistic.get(i), updatedStatistic.get(i));
                messageSender.sendTestMessageInChannelWithUserMention(message);
            }
        }
        System.out.println("ended");
    }
}
