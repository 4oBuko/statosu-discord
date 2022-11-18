package com.ua.statosudiscord.bot.slashcommands.handlers;

import com.ua.statosudiscord.bot.slashcommands.commands.UsernameSlashCommandBuilder;
import com.ua.statosudiscord.services.MessageService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
public class UsernameCommandHandler extends BaseCommandHandler {
    private final MessageService messageService;

    public UsernameCommandHandler(UsernameSlashCommandBuilder builder, MessageService messageService) {
        super(builder.build());
        this.messageService = messageService;
    }

    @Override
    public String executeCommand(SlashCommandInteractionEvent event) {
        String username = event.getOptionsByName("username").get(0).getAsString();
        long channelId = event.getInteraction().getChannel().getIdLong();
        long userId = event.getInteraction().getUser().getIdLong();
        String response = messageService.updateUsername(channelId, userId, username);
        event.reply(response).addContent(response).setEphemeral(true).queue();
        return null;
    }
}
