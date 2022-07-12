package com.ua.statosudiscord.bot;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.MessageChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    @Autowired
    GatewayDiscordClient gatewayDiscordClient;

    public void sendTestMessageInChannelWithUserMention(Message message) {
        gatewayDiscordClient.getChannelById(Snowflake.of(message.getDiscordChannelId()))
                .ofType(MessageChannel.class)
                .flatMap(channel -> channel.createMessage("<@" + message.getDiscordUserId() + ">\n" + message.getMessage()))
                .subscribe();
    }
}
