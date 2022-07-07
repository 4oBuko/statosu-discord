package com.ua.statosudiscord.bot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Message {
    private Long discordUserId;
    private Long discordChannelId;
    private String Message;
}
