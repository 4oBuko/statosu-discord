package com.ua.statosudiscord.bot.slashcommands;

import com.ua.statosudiscord.bot.slashcommands.commands.SlashCommandBuilder;
import com.ua.statosudiscord.bot.slashcommands.handlers.BaseCommandHandler;
import com.ua.statosudiscord.services.MessageService;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CommandsManager extends ListenerAdapter {
    private final MessageService messageService;
    private final List<BaseCommandHandler> commandHandlers;


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
//        this stream searches for a handler by the event name
//        if a handler exists event will get response created by the found handler
        commandHandlers.stream()
                .filter(h -> h.getCommandData().getName().equals(event.getName()))
                .findAny().ifPresent(
                        handler -> event.reply(handler.executeCommand(event)).setEphemeral(true).queue()
                );

//        if (event.getName().equals("username")) {
//
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        JDA jda = event.getJDA();
        CommandData[] commandsData = commandHandlers.stream()
                .map(BaseCommandHandler::getCommandData)
                .toArray(CommandData[]::new);
        jda.updateCommands().addCommands(commandsData).queue();
    }
}
