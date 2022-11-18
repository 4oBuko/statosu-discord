package com.ua.statosudiscord.bot.slashcommands.handlers;

import com.ua.statosudiscord.bot.slashcommands.commands.AboutSlashCommandBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
public class AboutCommandHandler extends BaseCommandHandler {
    private final static String aboutBot = """
            Hello, I'm Statosu. I show your progress in osu! classic mode.
            You need to give me your osu! username and the update time.
            I support these commands:
            - /about - about me
            - /username - set your osu! username
            - /update - set time for statistic update
            """;

    public AboutCommandHandler(AboutSlashCommandBuilder builder) {
        super(builder.build());
    }

    @Override
    public String executeCommand(SlashCommandInteractionEvent event) {
        return aboutBot;
    }
}
