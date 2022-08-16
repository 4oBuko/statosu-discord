package com.ua.statosudiscord.bot;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.MessageChannel;
import io.netty.channel.ChannelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {

    Logger logger = LoggerFactory.getLogger(MessageSender.class);
    @Autowired
    GatewayDiscordClient gatewayDiscordClient;

    public void sendTestMessageInChannelWithUserMention(Message message) {
        gatewayDiscordClient.getChannelById(Snowflake.of(message.getDiscordChannelId()))
                .ofType(MessageChannel.class)
                .flatMap(channel -> channel.createMessage("<@" + message.getDiscordUserId() + ">\n" + message.getMessage()))
                .doOnError(error -> logger.error(error.getMessage()))
                .subscribe();
    }
}
