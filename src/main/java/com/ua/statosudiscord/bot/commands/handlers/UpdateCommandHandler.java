package com.ua.statosudiscord.bot.commands.handlers;

import com.ua.statosudiscord.bot.commands.builders.UpdateCommandRequestBuilder;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.services.MessageService;
import com.ua.statosudiscord.services.UserService;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.discordjson.json.ApplicationCommandRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.DayOfWeek;

@Component
public class UpdateCommandHandler extends CommandHandler {
    private final UserService userService;

    private final MessageService messageService;

    @Autowired
    public UpdateCommandHandler(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
        UpdateCommandRequestBuilder updateCommandRequestBuilder = new UpdateCommandRequestBuilder();
        ApplicationCommandRequest commandRequest = updateCommandRequestBuilder.buildCommandRequest();
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
