package com.ua.statosudiscord.bot.commands.handlers;

import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.services.MessageService;
import com.ua.statosudiscord.services.UserService;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionChoiceData;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;

@Component
@AllArgsConstructor
public class UpdateCommandHandler extends CommandHandler {

    UserService userService;


    MessageService messageService;

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
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String response;
        long channelId = event.getInteraction().getChannelId().asLong();
        long userId = event.getInteraction().getUser().getId().asLong();
        User existedUser = userService.getUser(channelId, userId);
        int updateTime;
        int numberOfMonth = 0;
        DayOfWeek dayOfWeek = null;
        UpdatePeriod updatePeriod;
        ApplicationCommandInteractionOption updatePeriodOption;
        if (event.getOption("weekly").isPresent()) {
            updatePeriodOption = event.getOption("weekly").get();
            updatePeriod = UpdatePeriod.weekly;
        } else if (event.getOption("monthly").isPresent()) {
            updatePeriodOption = event.getOption("monthly").get();
            updatePeriod = UpdatePeriod.monthly;
        } else {
            updatePeriodOption = event.getOption("daily").get();
            updatePeriod = UpdatePeriod.daily;
        }
        if (updatePeriodOption.getOption("day-of-week").isPresent()) {
            dayOfWeek = DayOfWeek.valueOf(updatePeriodOption.getOption("day-of-week")
                    .flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asString)
                    .get().toUpperCase()
            );
        }
        if (updatePeriodOption.getOption("day").isPresent()) {
            numberOfMonth = updatePeriodOption.getOption("day")
                    .flatMap(ApplicationCommandInteractionOption::getValue)
                    .map(ApplicationCommandInteractionOptionValue::asLong)
                    .get()
                    .intValue();
        }
        updateTime = updatePeriodOption.getOption("time")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asLong)
                .get().intValue();
        if (existedUser == null) {
            response = "You cannot use this operation. Use !username command first";
        } else {
            existedUser.setUpdatePeriod(updatePeriod);
            existedUser.setUpdateTime(updateTime);
            existedUser.setDayOfMonth(numberOfMonth);
            existedUser.setDayOfWeek(dayOfWeek);
            User updatedUser = userService.changeUpdateInto(existedUser);
            response = messageService.getNewStatistic(updatedUser);
        }
        return event.reply()
                .withEphemeral(true)
                .withContent(response);
    }
}
