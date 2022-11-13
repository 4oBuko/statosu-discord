package com.ua.statosudiscord.bot.slashcommands.commands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.utils.data.DataObject;

public class UpdateSlashCommandBuilderBuilder implements SlashCommandBuilder {
    @Override
    public CommandData build() {
        return null;
    }

    private static SlashCommandData getDailyUpdateCommandOption() {
        return null;
    }

    private static SlashCommandData getWeeklyUpdateCommandOption() {
        return null;
    }

    private static SlashCommandData getMonthlyUpdateCommandOption() {
        return null;
    }

    private static SlashCommandData getUpdateTimeCommandOption() {
        return SlashCommandData.fromData(
                DataObject.fromJson("add json")
        );
    }
}
