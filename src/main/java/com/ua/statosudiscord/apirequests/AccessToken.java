package com.ua.statosudiscord.apirequests;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("expires_in")
    private int expiresIn;
    private LocalDateTime responseTime;
    @JsonProperty("access_token")
    private String token;
    @JsonProperty("token_type")
    private String tokenType;
}
