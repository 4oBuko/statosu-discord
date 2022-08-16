package com.ua.statosudiscord.apirequests.requests;

import com.ua.statosudiscord.apirequests.APIEndpoints;
import com.ua.statosudiscord.apirequests.TokenManager;
import com.ua.statosudiscord.persistence.entities.Statistic;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@AllArgsConstructor
public class OsuAPI {
    final Logger logger = LoggerFactory.getLogger(OsuAPI.class);
    @Autowired
    private TokenManager tokenManager;

    public Statistic getUserByUsername(String username) {
        RestTemplateBuilder templateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = templateBuilder.build();
        String url = APIEndpoints.GET_USER_CLASSIC;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", tokenManager.getToken().getTokenType() + " " + tokenManager.getToken().getToken());
        HttpEntity httpEntity = new HttpEntity(headers);
        try {
            ResponseEntity<Statistic> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Statistic.class, username);
            if (response.getStatusCode() == HttpStatus.OK) {
                Statistic statistic = response.getBody();
                HttpHeaders responseHeaders = response.getHeaders();
                statistic.setLastUpdated(LocalDateTime.ofEpochSecond(responseHeaders.getDate() / 1000, 0, ZoneOffset.UTC));
                return response.getBody();
            } else {
                logger.error("Request failed. Status code: " + response.getStatusCode());
                return null;
            }
        } catch (RestClientException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

//    todo:create method fog get multiple users when endpoint will be available
}
