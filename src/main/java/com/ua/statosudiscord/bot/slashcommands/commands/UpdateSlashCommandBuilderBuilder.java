package com.ua.statosudiscord.bot.slashcommands.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class UpdateSlashCommandBuilderBuilder implements SlashCommandBuilder {
    @Override
    public CommandData build() {
        return Commands.slash("update", "set statistic update period and time")
                .addSubcommands(getDailyUpdateCommandOption(),
                        getWeeklyUpdateCommandOption(),
                        getMonthlyUpdateCommandOption());
    }

    private static SubcommandData getDailyUpdateCommandOption() {
        return new SubcommandData("daily", "get statistic update everyday")
                .addOptions(getUpdateTimeCommandOption());
    }

    private static SubcommandData getWeeklyUpdateCommandOption() {
        OptionData daysOfWeek = new OptionData(OptionType.STRING, "day-of-week", "Choose day of the week for updates. You can write value in lowercase or uppercase.")
                .setRequired(true)
                .addChoice("Monday", DayOfWeek.MONDAY.name())
                .addChoice("Tuesday", DayOfWeek.TUESDAY.name())
                .addChoice("Wednesday", DayOfWeek.WEDNESDAY.name())
                .addChoice("Thursday", DayOfWeek.THURSDAY.name())
                .addChoice("Friday", DayOfWeek.FRIDAY.name())
                .addChoice("Saturday", DayOfWeek.SATURDAY.name())
                .addChoice("Sunday", DayOfWeek.SUNDAY.name());
        return new SubcommandData("weekly", "get statistic update every week")
                .addOptions(daysOfWeek, getUpdateTimeCommandOption());
    }

    private static SubcommandData getMonthlyUpdateCommandOption() {
        OptionData dayOfMonth = new OptionData(OptionType.INTEGER, "day", "day of the month (1-28)")
                .setRequired(true)
                .setMinValue(1L)
                .setMaxValue(28L);
        return new SubcommandData("monthly", "get statistic update every month")
                .addOptions(dayOfMonth);
    }

    private static OptionData getUpdateTimeCommandOption() {
        return new OptionData(OptionType.INTEGER, "time", "update time (0-23)")
                .setMinValue(0L)
                .setMaxValue(23L)
                .setRequired(true);
    }
}
