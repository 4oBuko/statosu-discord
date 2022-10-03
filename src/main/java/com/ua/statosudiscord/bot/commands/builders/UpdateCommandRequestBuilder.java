package com.ua.statosudiscord.bot.commands.builders;

import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;

public class UpdateCommandRequestBuilder implements CommandRequestBuilder {
    @Override
    public ApplicationCommandRequest buildCommandRequest() {
        return ApplicationCommandRequest.builder()
                .name("update")
                .description("set statistic update period and time")
                .addOption(buildDailyUpdateCommandOption())
                .addOption(buildWeeklyUpdatedCommandOption())
                .addOption(buildMonthlyUpdateOption())
                .build();
    }

    private static ApplicationCommandOptionData buildDailyUpdateCommandOption() {
        return ApplicationCommandOptionData.builder()
                .name("daily")
                .description("get statistic update everyday")
                .type(ApplicationCommandOption.Type.SUB_COMMAND.getValue())
                .addOption(buildUpdateTimeCommandOption())
                .build();
    }

    private static ApplicationCommandOptionData buildWeeklyUpdatedCommandOption() {
        return ApplicationCommandOptionData.builder()
                .name("weekly")
                .description("get statistic update every week")
                .type(ApplicationCommandOption.Type.SUB_COMMAND.getValue())
                .addOption(ApplicationCommandOptionData.builder()
                        .name("day-of-week")
                        .description("Choose day of the week for updates. You can write value in lowercase or uppercase.")
                        .addChoice(ApplicationCommandOptionChoiceData.builder()
                                .name("Monday")
                                .value("monday")
                                .build()
                        )
                        .addChoice(ApplicationCommandOptionChoiceData.builder()
                                .name("Tuesday")
                                .value("Tuesday")
                                .build()
                        )
                        .addChoice(ApplicationCommandOptionChoiceData.builder()
                                .name("Wednesday")
                                .value("Wednesday")
                                .build()
                        )
                        .addChoice(ApplicationCommandOptionChoiceData.builder()
                                .name("Thursday")
                                .value("Thursday")
                                .build()
                        )
                        .addChoice(ApplicationCommandOptionChoiceData.builder()
                                .name("Friday")
                                .value("Friday")
                                .build()
                        )
                        .addChoice(ApplicationCommandOptionChoiceData.builder()
                                .name("Saturday")
                                .value("Saturday")
                                .build()
                        )
                        .addChoice(ApplicationCommandOptionChoiceData.builder()
                                .name("Sunday")
                                .value("Sunday")
                                .build()
                        )
                        .type(ApplicationCommandOption.Type.STRING.getValue())
                        .required(true)
                        .build())
                .addOption(buildUpdateTimeCommandOption())
                .build();
    }

    public static ApplicationCommandOptionData buildMonthlyUpdateOption() {
        return ApplicationCommandOptionData.builder()
                .name("monthly")
                .description("get statistic update month")
                .type(ApplicationCommandOption.Type.SUB_COMMAND.getValue())
                .addOption(ApplicationCommandOptionData.builder()
                        .name("day")
                        .description("day of the month (1-28)")
                        .minValue(Double.valueOf(1L))
                        .maxValue(Double.valueOf(28))
                        .type(ApplicationCommandOption.Type.INTEGER.getValue())
                        .required(true)
                        .build()
                )
                .addOption(buildUpdateTimeCommandOption())
                .build();
    }

    public static ApplicationCommandOptionData buildUpdateTimeCommandOption() {
        return ApplicationCommandOptionData.builder()
                .name("time")
                .description("update time (0-23)")
                .minValue(Double.valueOf(0L))
                .maxValue(Double.valueOf(23))
                .type(ApplicationCommandOption.Type.INTEGER.getValue())
                .required(true)
                .build();
    }
}
