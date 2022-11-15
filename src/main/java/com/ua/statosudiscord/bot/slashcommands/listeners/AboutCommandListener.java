package com.ua.statosudiscord.bot.slashcommands.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class AboutCommandListener extends ListenerAdapter {
    private final static String aboutBot = """
            Hello, I'm Statosu. I show your progress in osu! classic mode.
            You need to give me your osu! username and the update time.
            I support these commands:
            - /about - about me
            - /username - set your osu! username
            - /update - set time for statistic update
            """;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("about")) {
            event.reply(aboutBot).queue(); // reply immediately
        }
    }
}
