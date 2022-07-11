package com.ua.statosudiscord.apirequests;

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
        if (accessToken == null || accessToken.getResponseTime().getSecond() > accessToken.getResponseTime().getSecond() + accessToken.getExpiresIn()) {
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
        System.out.println(response);
        if (response.getStatusCode() == HttpStatus.OK) {
            HttpHeaders headers = response.getHeaders();
            headers.getDate();
            accessToken = response.getBody();
            accessToken.setResponseTime(LocalDateTime.ofEpochSecond(headers.getDate() / 1000, 0, ZoneOffset.UTC));
        } else {
//            throw an error
        }
    }
}
