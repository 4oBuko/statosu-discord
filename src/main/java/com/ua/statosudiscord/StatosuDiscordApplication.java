package com.ua.statosudiscord;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StatosuDiscordApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatosuDiscordApplication.class, args);
    }

}
