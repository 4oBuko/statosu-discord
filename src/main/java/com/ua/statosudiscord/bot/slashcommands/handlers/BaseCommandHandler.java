package com.ua.statosudiscord.bot.slashcommands.handlers;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public abstract class BaseCommandHandler {

    private final CommandData commandData;

    public abstract String executeCommand(SlashCommandInteractionEvent event);

    public BaseCommandHandler(CommandData commandData) {
        this.commandData = commandData;
    }

    public CommandData getCommandData() {
        return commandData;
    }
}
