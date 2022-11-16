package com.ua.statosudiscord.bot.slashcommands.commands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.stereotype.Component;

@Component
public class AboutSlashCommandBuilder implements SlashCommandBuilder {
    @Override
    public CommandData build() {
        return commandData();
    }

    public static SlashCommandData commandData() {
        return Commands.slash("about", "info about me");
    }

}
