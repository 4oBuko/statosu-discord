package com.ua.statosudiscord.bot;

import com.ua.statosudiscord.apirequests.requests.OsuAPI;
import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.Statistic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessageBuilderTest {

    @Test
    public void createMessageWithOneParameter() {
        String message = "Username: testname\n" +
                "Global rank: 629286\n" +
                "pp: 1039.18\n" +
                "Level: 93.58\n" +
                "Accuracy: 89.8627\n" +
                "Play count: 15859\n" +
                "Playtime: 200 hr 22 min\n" +
                "A: 142\n" +
                "S: 33\n" +
                "SS: 4\n" +
                "S+: 1\n" +
                "SS+: 0\n" +
                "Updated on: Jul 11,2022";

        MessageBuilder messageBuilder = new MessageBuilder();
        StatisticBuilder statisticBuilder = new StatisticBuilder();
        Statistic statistic = statisticBuilder
                .setUsername("testname")
                .setGlobalRank(629286)
                .setPp(1039.18)
                .setLevel(93.58)
                .setHitAccuracy(89.8627)
                .setPlayCount(15859)
                .setPlayTime(200 * 3600 + 22 * 60)
                .setA(142)
                .setS(33)
                .setSs(4)
                .setSh(1)
                .setSsh(0)
                .setLastUpdated(LocalDateTime.of(2022, 7, 11, 12, 12)).build();
        assertEquals(message, messageBuilder.createMessage(statistic).getMessage());
    }
}