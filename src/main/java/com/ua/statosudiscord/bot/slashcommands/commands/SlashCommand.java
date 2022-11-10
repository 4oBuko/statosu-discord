package com.ua.statosudiscord.bot.slashcommands.commands;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface SlashCommand {
    CommandData getCommand();
}
