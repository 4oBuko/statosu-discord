package com.ua.statosudiscord.bot.slashcommands.handlers;

import com.ua.statosudiscord.bot.slashcommands.commands.UpdateSlashCommandBuilder;
import com.ua.statosudiscord.persistence.entities.UpdatePeriod;
import com.ua.statosudiscord.persistence.entities.User;
import com.ua.statosudiscord.services.MessageService;
import com.ua.statosudiscord.services.UserService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class UpdateCommandHandler extends BaseCommandHandler {

    private final UserService userService;

    private final MessageService messageService;

    public UpdateCommandHandler(UpdateSlashCommandBuilder builder, UserService userService, MessageService messageService) {
        super(builder.build());
        this.userService = userService;
        this.messageService = messageService;
    }

    @Override
    public String executeCommand(SlashCommandInteractionEvent event) {
        String response;
        long channelId = event.getInteraction().getChannel().getIdLong();
        long userId = event.getInteraction().getUser().getIdLong();
        User existedUser = userService.getUser(channelId, userId);
        int updateTime = event.getOption("time").getAsInt();
        int numberOfMonth = 0;
        DayOfWeek dayOfWeek = null;
        UpdatePeriod updatePeriod;
        updatePeriod = UpdatePeriod.valueOf(event.getSubcommandName());
        if (event.getOption("day-of-week") != null) {
            dayOfWeek = DayOfWeek.valueOf(event.getOption("day-of-week").getAsString());
        }
        if (event.getOption("day") != null) {
            numberOfMonth = event.getOption("day").getAsInt();
        }
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
        return response;
    }
}
