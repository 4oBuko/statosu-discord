package com.ua.statosudiscord.bot.commands.handlers;

import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UpdateCommandHandler extends CommandHandler {
    public UpdateCommandHandler() {
        ApplicationCommandRequest commandRequest = ApplicationCommandRequest.builder()
                .name("update")
                .description("set statistic update period and time")
                .addOption(ApplicationCommandOptionData.builder()
                        .name("daily")
                        .description("get statistic update everyday")
                        .type(ApplicationCommandOption.Type.SUB_COMMAND.getValue())
                        .addOption(ApplicationCommandOptionData.builder()
                                .name("time")
                                .description("update time (0-23)")
                                .minValue(Double.valueOf(0L))
                                .maxValue(Double.valueOf(23))
                                .type(ApplicationCommandOption.Type.INTEGER.getValue())
                                .required(true)
                                .build()
                        )
                        .build()
                )
                .addOption(ApplicationCommandOptionData.builder()
                        .name("weekly")
                        .description("get statistic update every week")
                        .type(ApplicationCommandOption.Type.SUB_COMMAND.getValue())
                        .addOption(ApplicationCommandOptionData.builder()
                                .name("day-of-week")
                                .description("Choose day of the week for updates. You can write value in lowercase or uppercase.")
                                .type(ApplicationCommandOption.Type.STRING.getValue())
                                .required(true)
                                .build())
                        .addOption(ApplicationCommandOptionData.builder()
                                .name("time")
                                .description("update time (0-23)")
                                .minValue(Double.valueOf(0L))
                                .maxValue(Double.valueOf(23))
                                .type(ApplicationCommandOption.Type.INTEGER.getValue())
                                .required(true)
                                .build()
                        )
                        .build()
                )
                .addOption(ApplicationCommandOptionData.builder()
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
                        .addOption(ApplicationCommandOptionData.builder()
                                .name("time")
                                .description("update time (0-23)")
                                .minValue(Double.valueOf(0L))
                                .maxValue(Double.valueOf(23))
                                .type(ApplicationCommandOption.Type.INTEGER.getValue())
                                .required(true)
                                .build()
                        )
                        .build()
                )
                .build();

        setCommandRequest(commandRequest);
    }

    @Override
    public Mono<Void> handle() {
        return null;
    }
}
