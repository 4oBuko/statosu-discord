package com.ua.statosudiscord.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ua.statosudiscord.persistence.builders.StatisticBuilder;
import com.ua.statosudiscord.persistence.entities.Statistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class StatisticJSONParser {
    private static Logger logger = LoggerFactory.getLogger(StatisticJSONParser.class);
    public static List<Statistic> parseStatistic(String json) {
        List<Statistic> statisticList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            ArrayNode users = (ArrayNode) jsonNode.get("users");
            for (int i = 0; i < users.size(); i++) {
                StatisticBuilder statisticBuilder = new StatisticBuilder();
                statisticBuilder.setOsuId(users.get(i).get("id").asLong());
                JsonNode statisticRulesets = users.get(i).get("statistics_rulesets");
                JsonNode osu = statisticRulesets.get("osu");
                JsonNode level = osu.get("level");
                statisticBuilder.setLevel(level.get("current").asInt() + level.get("progress").asInt() / 100.0);
                statisticBuilder.setGlobalRank(osu.get("global_rank").asInt());
                statisticBuilder.setPp(osu.get("pp").asDouble());
                statisticBuilder.setHitAccuracy(osu.get("hit_accuracy").asDouble());
                statisticBuilder.setPlayCount(osu.get("play_count").asInt());
                statisticBuilder.setPlayTime(osu.get("play_time").asInt());
                JsonNode grades = osu.get("grade_counts");
                statisticBuilder.setA(grades.get("a").asInt());
                statisticBuilder.setS(grades.get("s").asInt());
                statisticBuilder.setSs(grades.get("ss").asInt());
                statisticBuilder.setSh(grades.get("sh").asInt());
                statisticBuilder.setSsh(grades.get("ssh").asInt());
                statisticList.add(statisticBuilder.build());
            }
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        return statisticList;
    }
}
