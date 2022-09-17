package com.ua.statosudiscord.utils;

import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.persistence.entities.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticJSONParserTest {

    StatisticBuilder user1Builder = new StatisticBuilder()
            .setUser(new User())
            .setId(1L)
            .setOsuId(1L)
            .setGlobalRank(11)
            .setLevel(11.5)
            .setPp(5000.2)
            .setPlayTime(123321)
            .setPlayCount(10000)
            .setHitAccuracy(93.2)
            .setA(1)
            .setS(2)
            .setSs(3)
            .setSh(0)
            .setSsh(2);
    Statistic user1 = user1Builder.build();

    StatisticBuilder user2Builder = new StatisticBuilder()
            .setUser(new User())
            .setId(2L)
            .setOsuId(2L)
            .setGlobalRank(111)
            .setLevel(111.5)
            .setPp(4500.28)
            .setPlayTime(111111)
            .setPlayCount(8234)
            .setHitAccuracy(91.4)
            .setA(500)
            .setS(140)
            .setSs(20)
            .setSh(21)
            .setSsh(0);
    Statistic user2 = user1Builder.build();

    List<Statistic> testUsers = new LinkedList<>(Arrays.stream(new Statistic[] {user1,user2}).toList());


    @Test
    public void emptyResponse() {
        String json = "{\"users\": []\n}";
        List<Statistic> list = StatisticJSONParser.parseStatistic(json);
        assertTrue(list.isEmpty());
    }

    @Test
    public void oneUserResponse() {
        String json = "{\n" +
                "    \"users\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"username\": \"test\",\n" +
                "            \"statistics_rulesets\": {\n" +
                "                \"osu\": {\n" +
                "                    \"level\": {\n" +
                "                        \"current\": 93,\n" +
                "                        \"progress\": 20\n" +
                "                    },\n" +
                "                    \"global_rank\": 11,\n" +
                "                    \"pp\": 5000.2,\n" +
                "                    \"hit_accuracy\": 93.2,\n" +
                "                    \"play_count\": 10000,\n" +
                "                    \"play_time\": 123321,\n" +
                "                    \"grade_counts\": {\n" +
                "                        \"ss\": 3,\n" +
                "                        \"ssh\": 2,\n" +
                "                        \"s\": 2,\n" +
                "                        \"sh\": 0,\n" +
                "                        \"a\": 1\n" +
                "                    }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "    ]\n" +
                "}";
        Statistic statistic = StatisticJSONParser.parseStatistic(json).get(0);
        statistic.setId(user1.getId());
        statistic.setUser(user1.getUser());
        assertEquals(user1, statistic);
    }

    @Test
    public void twoUsersResponse() {
        String json = "{\n" +
                "    \"users\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"username\": \"test\",\n" +
                "            \"statistics_rulesets\": {\n" +
                "                \"osu\": {\n" +
                "                    \"level\": {\n" +
                "                        \"current\": 93,\n" +
                "                        \"progress\": 20\n" +
                "                    },\n" +
                "                    \"global_rank\": 11,\n" +
                "                    \"pp\": 5000.2,\n" +
                "                    \"hit_accuracy\": 93.2,\n" +
                "                    \"play_count\": 10000,\n" +
                "                    \"play_time\": 123321,\n" +
                "                    \"grade_counts\": {\n" +
                "                        \"ss\": 0,\n" +
                "                        \"ssh\": 0,\n" +
                "                        \"s\": 44,\n" +
                "                        \"sh\": 0,\n" +
                "                        \"a\": 169\n" +
                "                    }\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "{\n" +
                "            \"id\":2,\n" +
                "            \"username\": \"test2\",\n" +
                "            \"statistics_rulesets\": {\n" +
                "                \"osu\": {\n" +
                "                    \"level\": {\n" +
                "                        \"current\": 111,\n" +
                "                        \"progress\": 5\n" +
                "                    },\n" +
                "                    \"global_rank\": 111,\n" +
                "                    \"pp\": 4500.28,\n" +
                "                    \"hit_accuracy\": 91.4,\n" +
                "                    \"play_count\": 8234,\n" +
                "                    \"play_time\": 111111,\n" +
                "                    \"grade_counts\": {\n" +
                "                        \"ss\": 20,\n" +
                "                        \"ssh\": 0,\n" +
                "                        \"s\": 140,\n" +
                "                        \"sh\": 21,\n" +
                "                        \"a\": 500\n" +
                "                    }\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "    ]\n" +
                "}";

        List<Statistic> statisticList = StatisticJSONParser.parseStatistic(json);
        statisticList.get(0).setId(user1.getId());
        statisticList.get(1).setId(user2.getId());
        statisticList.get(0).setUser(user1.getUser());
        statisticList.get(1).setUser(user2.getUser());

        assertEquals(testUsers, statisticList);
    }
}