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

    final StatisticBuilder user1Builder = new StatisticBuilder()
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
    final Statistic user1 = user1Builder.build();

    final StatisticBuilder user2Builder = new StatisticBuilder()
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
    final Statistic user2 = user2Builder.build();

    final List<Statistic> testUsers = new LinkedList<>(Arrays.stream(new Statistic[] {user1,user2}).toList());


    @Test
    public void emptyResponse() {
        String json = "{\"users\": []\n}";
        List<Statistic> list = StatisticJSONParser.parseStatistic(json);
        assertTrue(list.isEmpty());
    }

    @Test
    public void oneUserResponse() {
        String json = """
                {
                    "users": [
                        {
                            "id": 1,
                            "username": "test",
                            "statistics_rulesets": {
                                "osu": {
                                    "level": {
                                        "current": 93,
                                        "progress": 20
                                    },
                                    "global_rank": 11,
                                    "pp": 5000.2,
                                    "hit_accuracy": 93.2,
                                    "play_count": 10000,
                                    "play_time": 123321,
                                    "grade_counts": {
                                        "ss": 3,
                                        "ssh": 2,
                                        "s": 2,
                                        "sh": 0,
                                        "a": 1
                                    }
                            }
                        }
                    }
                    ]
                }""";
        Statistic statistic = StatisticJSONParser.parseStatistic(json).get(0);
        statistic.setId(user1.getId());
        statistic.setUser(user1.getUser());
        assertEquals(user1, statistic);
    }

    @Test
    public void twoUsersResponse() {
        String json = """
                {
                    "users": [
                        {
                            "id": 1,
                            "username": "test",
                            "statistics_rulesets": {
                                "osu": {
                                    "level": {
                                        "current": 93,
                                        "progress": 20
                                    },
                                    "global_rank": 11,
                                    "pp": 5000.2,
                                    "hit_accuracy": 93.2,
                                    "play_count": 10000,
                                    "play_time": 123321,
                                    "grade_counts": {
                                        "ss": 0,
                                        "ssh": 0,
                                        "s": 44,
                                        "sh": 0,
                                        "a": 169
                                    }
                            }
                        }
                    },
                {
                            "id":2,
                            "username": "test2",
                            "statistics_rulesets": {
                                "osu": {
                                    "level": {
                                        "current": 111,
                                        "progress": 5
                                    },
                                    "global_rank": 111,
                                    "pp": 4500.28,
                                    "hit_accuracy": 91.4,
                                    "play_count": 8234,
                                    "play_time": 111111,
                                    "grade_counts": {
                                        "ss": 20,
                                        "ssh": 0,
                                        "s": 140,
                                        "sh": 21,
                                        "a": 500
                                    }
                            }
                        }
                    }
                    ]
                }""";

        List<Statistic> statisticList = StatisticJSONParser.parseStatistic(json);
        statisticList.get(0).setId(user1.getId());
        statisticList.get(1).setId(user2.getId());
        statisticList.get(0).setUser(user1.getUser());
        statisticList.get(1).setUser(user2.getUser());

        assertEquals(testUsers, statisticList);
    }
}