package com.ua.statosudiscord.utils.caching;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ua.statosudiscord.apirequests.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class TokenObjectSerializer implements ObjectSerializer<AccessToken> {
    private static final Logger logger = LoggerFactory.getLogger(TokenObjectSerializer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    {
        objectMapper.findAndRegisterModules();
    }

    @Override
    public void cacheObject(AccessToken object, String path) {
        try {
            objectMapper.writeValue(new File(path), object);
        } catch (IOException e) {
            logger.error("Serialization failed: " + e.getMessage());
        }
    }

    @Override
    public AccessToken getFromCache(String path) {
        try {
            return objectMapper.readValue(new File(path), AccessToken.class);
        } catch (IOException e) {
            logger.error("Deserialization failed:" + e.getMessage());
            return null;
        }
    }
}
