package com.ua.statosudiscord.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private Long id;
    private Long channelId;
    private Long userId;
    private String osuUsername;

    public User(Long channelId, Long userId, String osuUsername) {
        this.channelId = channelId;
        this.userId = userId;
        this.osuUsername = osuUsername;
    }
}
