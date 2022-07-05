package com.ua.statosudiscord.apirequests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken implements Serializable {
    private int expiresIn;
    private LocalDateTime responseTime;
    private String token;
    private String tokenType;
}
