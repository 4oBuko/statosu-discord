package com.ua.statosudiscord.bot.slashcommands;

import com.ua.statosudiscord.bot.slashcommands.commands.SlashCommandBuilder;
import com.ua.statosudiscord.services.MessageService;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CommandsManager extends ListenerAdapter {
    private final MessageService messageService;
    private final List<SlashCommandBuilder> builders;


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("username")) {
            String username = event.getOptionsByName("username").get(0).getAsString();
            long channelId = event.getInteraction().getChannel().getIdLong();
            long userId = event.getInteraction().getUser().getIdLong();
            String response = messageService.updateUsername(channelId, userId, username);
            event.reply(response).setEphemeral(true).queue();
        } else if (event.getName().equals("about")) {
            event.reply("about").setEphemeral(true).queue();
        } else if (event.getName().equals("update")) {
            event.reply("update").setEphemeral(true).queue();
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        JDA jda = event.getJDA();
        for (SlashCommandBuilder builder : builders) {
            jda.updateCommands().addCommands(builder.build()).queue();
        }
//        CommandData[] commandDataList = builders.stream()
//                .map(SlashCommandBuilder::build).toArray(CommandData[]::new);
//        jda.updateCommands().addCommands(commandDataList).queue();
    }
}
