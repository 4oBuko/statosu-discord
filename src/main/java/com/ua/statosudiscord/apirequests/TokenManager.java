package com.ua.statosudiscord.apirequests;

import com.ua.statosudiscord.utils.caching.TokenObjectSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenManager {
    private static final Logger logger = LoggerFactory.getLogger(TokenManager.class);
    private static final String CACHE_PATH = ".cache/token.json";
    private final TokenObjectSerializer tokenObjectSerializer = new TokenObjectSerializer();
    private static String clientSecret;
    private static String clientId;
    private AccessToken accessToken;

    @Value("${client.secret}")
    public void setClientSecret(String clientSecret) {
        TokenManager.clientSecret = clientSecret;
    }

    @Value("${client.id}")
    public void setClientId(String clientId) {
        TokenManager.clientId = clientId;
    }

    public AccessToken getToken() {
        if (accessToken == null) {
            accessToken = tokenObjectSerializer.getFromCache(CACHE_PATH);
        }
        //generate new token if cache is empty or token is expired
        if (accessToken == null
                || LocalDateTime.now().isAfter(accessToken.getResponseTime().plusSeconds(accessToken.getExpiresIn()))
                || LocalDateTime.now().isEqual(accessToken.getResponseTime().plusSeconds(accessToken.getExpiresIn()))) {
            generateAccessToken();
        }
        return accessToken;
    }

    private void generateAccessToken() {
        String requestURL = APIEndpoints.GET_TOKEN;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("client_id", clientId);
        requestBody.put("client_secret", clientSecret);
        requestBody.put("grant_type", "client_credentials");
        requestBody.put("scope", "public");
        ResponseEntity<AccessToken> response = restTemplate.postForEntity(requestURL, requestBody, AccessToken.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            HttpHeaders headers = response.getHeaders();
            headers.getDate();
            accessToken = response.getBody();
            accessToken.setResponseTime(LocalDateTime.ofEpochSecond(headers.getDate() / 1000, 0, ZoneOffset.UTC));
            tokenObjectSerializer.cacheObject(accessToken, CACHE_PATH);
        } else {
            logger.error("Access token request failed: Http status:" + response.getStatusCode());
            accessToken = null;
        }
    }
}
