package com.ua.statosudiscord.bot;

import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.Statistic;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessageBuilderTest {

    StatisticBuilder oldStatisticBuilder = new StatisticBuilder()
            .setUsername("testname")
            .setGlobalRank(629286)
            .setPp(1039.18)
            .setLevel(93.58)
            .setHitAccuracy(89.8627)
            .setPlayCount(17000)
            .setPlayTime(200 * 3600 + 22 * 60)
            .setA(142)
            .setS(33)
            .setSs(4)
            .setSh(1)
            .setSsh(0)
            .setLastUpdated(LocalDateTime.of(2022, 7, 11, 12, 12));
    StatisticBuilder newStatisticBuilder = new StatisticBuilder()
            .setUsername("testname")
            .setGlobalRank(600000)
            .setPp(1100.05)
            .setLevel(95.3)
            .setHitAccuracy(92.45)
            .setPlayCount(17000)
            .setPlayTime(200 * 3600 + 22 * 60)
            .setA(150)
            .setS(36)
            .setSs(6)
            .setSh(2)
            .setSsh(1)
            .setLastUpdated(LocalDateTime.of(2022, 7, 29, 12, 12));
    Statistic oldStatistic = oldStatisticBuilder.build();
    Statistic newStatistic = newStatisticBuilder.build();

    @Test
    public void createMessageWithOneParameter() {
        String message = "Username: testname\n" +
                "Global rank: 629286\n" +
                "pp: 1039.18\n" +
                "Level: 93.58\n" +
                "Accuracy: 89.8627\n" +
                "Play count: 17000\n" +
                "Playtime: 200 hr 22 min\n" +
                "A: 142\n" +
                "S: 33\n" +
                "SS: 4\n" +
                "S+: 1\n" +
                "SS+: 0\n" +
                "Updated on: Jul 11,2022";
        assertEquals(message, MessageBuilder.createMessage(oldStatistic).getMessage());
    }

    @Test
    public void createMessageWithTwoParametersProgressInAllFields() {
        String message = "Username: testname\n" +
                "Global rank: 600000(+29286)\n" +
                "pp: 1100.05(+60.87)\n" +
                "Level: 95.3(+1.72)\n" +
                "Accuracy: 92.45(+2.59)\n" +
                "Play count: 17000(+0)\n" +
                "Playtime: 200 hr 22 min(+0 min)\n" +
                "A: 150(+8)\n" +
                "S: 36(+3)\n" +
                "SS: 6(+2)\n" +
                "S+: 2(+1)\n" +
                "SS+: 1(+1)\n" +
                "Updated on: Jul 29,2022";
        assertEquals(message, MessageBuilder.createMessage(oldStatistic, newStatistic).getMessage());
    }

    @Test
    public void createMessageWithTwoParametersRegressInAllFields() {
        String message = "Username: testname\n" +
                "Global rank: 629286(-29286)\n" +
                "pp: 1039.18(-60.87)\n" +
                "Level: 93.58(-1.72)\n" +
                "Accuracy: 89.8627(-2.59)\n" +
                "Play count: 17000(+0)\n" +
                "Playtime: 200 hr 22 min(+0 min)\n" +
                "A: 142(-8)\n" +
                "S: 33(-3)\n" +
                "SS: 4(-2)\n" +
                "S+: 1(-1)\n" +
                "SS+: 0(-1)\n" +
                "Updated on: Jul 11,2022";
        assertEquals(message, MessageBuilder.createMessage(newStatistic, oldStatistic).getMessage());
    }

    @Test
    public void createMessageWithTwoParametersNoProgress() {
        String message = "Username: testname\n" +
                "Global rank: 629286(+0)\n" +
                "pp: 1039.18(+0.00)\n" +
                "Level: 93.58(+0.00)\n" +
                "Accuracy: 89.8627(+0.00)\n" +
                "Play count: 17000(+0)\n" +
                "Playtime: 200 hr 22 min(+0 min)\n" +
                "A: 142(+0)\n" +
                "S: 33(+0)\n" +
                "SS: 4(+0)\n" +
                "S+: 1(+0)\n" +
                "SS+: 0(+0)\n" +
                "Updated on: Jul 11,2022";
        assertEquals(message, MessageBuilder.createMessage(oldStatistic, oldStatistic).getMessage());
    }
}