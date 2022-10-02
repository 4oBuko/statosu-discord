package com.ua.statosudiscord.bot.commands.handlers;

import com.ua.statosudiscord.bot.Message;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageSenderJDA {
    private JDA jda;

    public void sendMessageForUser(Message message) {
        TextChannel textChannel = jda.getTextChannelById(message.getDiscordChannelId());
        if(textChannel != null) {
            textChannel.sendMessage("<@" + message.getDiscordUserId() + ">\n" + message.getMessage()).queue();
        }
    }
}
