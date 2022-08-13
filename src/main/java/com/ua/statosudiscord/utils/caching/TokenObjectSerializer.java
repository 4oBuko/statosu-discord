package com.ua.statosudiscord.utils.caching;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.statosudiscord.apirequests.AccessToken;

import java.io.File;
import java.io.IOException;

public class TokenObjectSerializer implements ObjectSerializer<AccessToken> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        objectMapper.findAndRegisterModules();
    }

    @Override
    public void cacheObject(AccessToken object, String path) {
        try {
            objectMapper.writeValue(new File(path), object);
        } catch (IOException e) {
            System.out.println("Serialization failed");
        }
    }

    @Override
    public AccessToken getFromCache(String path) {
        try {
            return objectMapper.readValue(new File(path), AccessToken.class);
        } catch (IOException e) {
            System.out.println("Deserialization failed");
            return null;
        }
    }
}
