package com.ua.statosudiscord.bot.slashcommands.commands;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

@Component
public class UsernameSlashCommandBuilder implements SlashCommandBuilder {
    @Override
    public CommandData build() {
        return commandData();
    }

    private static SlashCommandData commandData() {
        return Commands.slash("username", "set your osu! username")
                .addOption(OptionType.STRING, "username", "your osu! username", true);
    }

}
