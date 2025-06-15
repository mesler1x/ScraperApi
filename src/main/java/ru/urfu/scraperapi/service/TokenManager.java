package ru.urfu.scraperapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.urfu.scraperapi.dto.AccessTokenResponse;

import java.util.UUID;

import static ru.urfu.scraperapi.config.RestTemplateConfig.GIGA_CHAT_REST_TEMPLATE_BEAN_NAME;

@Service
@Slf4j
public class TokenManager {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private long expTime = 0L;
    private String refreshToken = "";

    public TokenManager(@Qualifier(GIGA_CHAT_REST_TEMPLATE_BEAN_NAME) RestTemplate restTemplate,
                        ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        try {
            getToken();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public String getToken() {
        if (isTokenExpired()) {
            var authKey = "ZTM4YTA3MjMtNDlhYy00MmZkLWEwMjgtYzNlM2MyMTMyY2EzOjcxYWVlZTQ4LThiZmItNGY1MC1iNWE1LWZlOTU5NDI3ODIwZg==";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Accept", "application/json");
            headers.set("RqUID", UUID.randomUUID().toString());
            headers.set("Authorization", "Basic " + authKey);

            String body = "scope=GIGACHAT_API_PERS";
            HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
            String response = restTemplate.exchange(
                    "https://ngw.devices.sberbank.ru:9443/api/v2/oauth",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            ).getBody();

            try {
                AccessTokenResponse tokenResponse = objectMapper.readValue(response, AccessTokenResponse.class);
                expTime = tokenResponse.expiresAt();
                refreshToken = tokenResponse.accessToken();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return refreshToken;
    }

    public boolean isTokenExpired() {
        return System.currentTimeMillis() + 3000L >= expTime;
    }
}