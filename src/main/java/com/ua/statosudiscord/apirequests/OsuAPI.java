package com.ua.statosudiscord.apirequests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ua.statosudiscord.persistence.entities.Statistic;
import com.ua.statosudiscord.utils.StatisticJSONParser;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OsuAPI {
    final Logger logger = LoggerFactory.getLogger(OsuAPI.class);
    @Autowired
    private TokenManager tokenManager;

    public ResponseEntity<Statistic> getUserByUsername(String username) {
        RestTemplateBuilder templateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = templateBuilder.build();
        String url = APIEndpoints.GET_USER_CLASSIC;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenManager.getToken().getTokenType() + " " + tokenManager.getToken().getToken());
        HttpEntity httpEntity = new HttpEntity(headers);
        try {
            ResponseEntity<Statistic> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Statistic.class, username);
            return response;
        } catch (RestClientException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public ResponseEntity<String> getMultipleUsers(List<Long> userIDs) {
        RestTemplateBuilder templateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = templateBuilder.build();
        String endpoint = APIEndpoints.GET_MULTIPLE_USERS_CLASSIC;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenManager.getToken().getTokenType() + " " + tokenManager.getToken().getToken());
        HttpEntity httpEntity = new HttpEntity(headers);
        String url = endpoint + "?" + userIDs.stream().map(x -> "ids[]=" + x + "&").collect(Collectors.joining());
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            return response;
        } catch (RestClientException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
