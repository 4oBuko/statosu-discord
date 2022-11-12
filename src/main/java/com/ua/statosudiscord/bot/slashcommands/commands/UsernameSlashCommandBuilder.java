package com.ua.statosudiscord.bot.slashcommands.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class UsernameSlashCommandBuilder implements SlashCommand {
    @Override
    public CommandData getCommand() {
        return commandData();
    }

    private static SlashCommandData commandData() {
        return Commands.slash("username", "set your osu! username")
                .addOption(OptionType.STRING, "username", "your osu! username", true);
    }

}
