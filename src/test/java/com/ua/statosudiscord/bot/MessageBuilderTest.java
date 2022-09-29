package com.ua.statosudiscord.bot;

import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.utils.MessageBuilder;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessageBuilderTest {

    final StatisticBuilder oldStatisticBuilder = new StatisticBuilder()
            .setUser(new User(1L, 1L, 1L, "testname", UpdatePeriod.daily, DayOfWeek.MONDAY, 0, 1))
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
            .setLastUpdated(LocalDateTime.of(2022, 7, 11, 12, 12))
            .setNextUpdateTime(LocalDateTime.of(2022, 7, 18, 12, 0));
    final StatisticBuilder newStatisticBuilder = new StatisticBuilder()
            .setUser(new User(1L, 1L, 1L, "testname", UpdatePeriod.daily, DayOfWeek.MONDAY, 0, 1))
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
            .setLastUpdated(LocalDateTime.of(2022, 7, 30, 12, 12))
            .setNextUpdateTime(LocalDateTime.of(2022, 8, 6, 12, 0));

    final Statistic oldStatistic = oldStatisticBuilder.build();
    final Statistic newStatistic = newStatisticBuilder.build();

    @Test
    public void createMessageWithOneParameter() {
        String message = """
                Username: testname
                Global rank: 629286
                pp: 1039.18
                Level: 93.58
                Accuracy: 89.8627
                Play count: 17000
                Playtime: 200 hr 22 min
                A: 142
                S: 33
                SS: 4
                S+: 1
                SS+: 0
                Updated on: 12:12 Jul 11,2022 (UTC)
                Next update: 12:00 Jul 18,2022 (UTC)""";
        assertEquals(message, MessageBuilder.createMessage(oldStatistic).getMessage());
    }

    @Test
    public void createMessageWithTwoParametersProgressInAllFields() {
        String message = """
                Previous update: 12:12 Jul 11,2022
                Username: testname
                Global rank: 600000(+29286)
                pp: 1100.05(+60.87)
                Level: 95.3(+1.72)
                Accuracy: 92.45(+2.59)
                Play count: 17000(+0)
                Playtime: 200 hr 22 min(+0 min)
                A: 150(+8)
                S: 36(+3)
                SS: 6(+2)
                S+: 2(+1)
                SS+: 1(+1)
                Updated on: 12:12 Jul 30,2022 (UTC)
                Next update: 12:00 Aug 06,2022 (UTC)""";
        assertEquals(message, MessageBuilder.createMessage(oldStatistic, newStatistic).getMessage());
    }

    @Test
    public void createMessageWithTwoParametersRegressInAllFields() {
        String message = """
                Previous update: 12:12 Jul 30,2022
                Username: testname
                Global rank: 629286(-29286)
                pp: 1039.18(-60.87)
                Level: 93.58(-1.72)
                Accuracy: 89.8627(-2.59)
                Play count: 17000(+0)
                Playtime: 200 hr 22 min(+0 min)
                A: 142(-8)
                S: 33(-3)
                SS: 4(-2)
                S+: 1(-1)
                SS+: 0(-1)
                Updated on: 12:12 Jul 11,2022 (UTC)
                Next update: 12:00 Jul 18,2022 (UTC)""";
        assertEquals(message, MessageBuilder.createMessage(newStatistic, oldStatistic).getMessage());
    }

    @Test
    public void createMessageWithTwoParametersNoProgress() {
        String message = """
                Previous update: 12:12 Jul 11,2022
                Username: testname
                Global rank: 629286(+0)
                pp: 1039.18(+0.00)
                Level: 93.58(+0.00)
                Accuracy: 89.8627(+0.00)
                Play count: 17000(+0)
                Playtime: 200 hr 22 min(+0 min)
                A: 142(+0)
                S: 33(+0)
                SS: 4(+0)
                S+: 1(+0)
                SS+: 0(+0)
                Updated on: 12:12 Jul 11,2022 (UTC)
                Next update: 12:00 Jul 18,2022 (UTC)""";
        assertEquals(message, MessageBuilder.createMessage(oldStatistic, oldStatistic).getMessage());
    }
}