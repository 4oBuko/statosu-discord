package com.ua.statosudiscord.bot.slashcommands;

import com.ua.statosudiscord.bot.slashcommands.handlers.BaseCommandHandler;
import com.ua.statosudiscord.services.MessageService;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CommandsManager extends ListenerAdapter {
    private final static Logger LOG = LoggerFactory.getLogger(CommandsManager.class);
    private final List<BaseCommandHandler> commandHandlers;


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
//        this stream searches for a handler by the event name
//        if the handler exists event will get response created by this handler
        commandHandlers.stream()
                .filter(h -> h.getCommandData().getName().equals(event.getName()))
                .findAny().ifPresent(
                        handler -> event.reply(handler.executeCommand(event)).setEphemeral(true).queue()
                );

    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        JDA jda = event.getJDA();
        List<String> commands = jda.retrieveCommands().complete().stream().map(Command::getName).toList();
        CommandData[] commandsData = commandHandlers.stream()
                .map(BaseCommandHandler::getCommandData)
                .filter(cd -> !commands.contains(cd.getName()))//omit registered commands
                .toArray(CommandData[]::new);
        if(commandsData.length != 0) {
            jda.updateCommands().addCommands(commandsData).queue();
        }
        LOG.debug("slash commands was added successfully");
    }
}
