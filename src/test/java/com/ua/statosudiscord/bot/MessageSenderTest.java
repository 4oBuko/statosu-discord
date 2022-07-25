package com.ua.statosudiscord.bot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MessageSenderTest {

    @Autowired
    MessageSender messageSender;
    @Test
    void sendTestMessageInChannelWithUserMention() {
        messageSender.sendTestMessageInChannelWithUserMention(new Message(1L,1L,"test"));
    }
}