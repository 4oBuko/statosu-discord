package com.ua.statosudiscord.bot;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.MessageChannel;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageSender {

    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);
    private final GatewayDiscordClient gatewayDiscordClient;

    public void sendMessageInChannelWithUserMention(Message message) {
        gatewayDiscordClient.getChannelById(Snowflake.of(message.getDiscordChannelId()))
                .ofType(MessageChannel.class)
                .flatMap(channel -> channel.createMessage("<@" + message.getDiscordUserId() + ">\n" + message.getMessage()))
                .doOnError(error -> logger.error(error.getMessage()))
                .subscribe();
    }

    public void sendMessageInChannelWithoutMentioningUser(Message message) {
        //todo: mention user in the message
        gatewayDiscordClient
                .rest()
                .getChannelById(Snowflake.of(message.getDiscordChannelId()))
                .createMessage(message.getMessage())
                .subscribe();
    }
}
