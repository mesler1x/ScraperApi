package ru.urfu.scraperapi.service.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.urfu.scraperapi.dto.CompletionRequest;
import ru.urfu.scraperapi.dto.GigaChatResponse;
import ru.urfu.scraperapi.dto.ModelSummaryRequest;
import ru.urfu.scraperapi.dto.ModelSummaryResponse;
import ru.urfu.scraperapi.jpa.entity.Publication;
import ru.urfu.scraperapi.model.Message;
import ru.urfu.scraperapi.service.TokenManager;

import java.util.Collections;

import static ru.urfu.scraperapi.config.RestTemplateConfig.GIGA_CHAT_REST_TEMPLATE_BEAN_NAME;

@Service
@RequiredArgsConstructor
public class SummaryModelRestClient {
    private final RestTemplate restTemplate;
    private final TokenManager tokenManager;
    @Qualifier(GIGA_CHAT_REST_TEMPLATE_BEAN_NAME)
    private final RestTemplate gigaChatRestTemplate;

    @Value("${scrape.model.summary-url}")
    private String summaryUrl;

    @Value("${scrape.is-giga-chat-enabled}")
    private boolean isGigaChatEnabled;

    public ModelSummaryResponse getSummaryFromPublication(Publication publication) {
        if (isGigaChatEnabled) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(tokenManager.getToken());

            CompletionRequest request = new CompletionRequest(
                    "GigaChat",
                    Collections.singletonList(new Message("user", publication.getText())),
                    0.4
            );

            HttpEntity<CompletionRequest> requestEntity = new HttpEntity<>(request, headers);
            GigaChatResponse response = gigaChatRestTemplate.exchange(
                    "https://gigachat.devices.sberbank.ru/api/v1/chat/completions",
                    HttpMethod.POST,
                    requestEntity,
                    GigaChatResponse.class
            ).getBody();

            return new ModelSummaryResponse(response.choices().get(0).getMessage().getContent());
        }
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var httpEntity = new HttpEntity<>(ModelSummaryRequest.from(publication), headers);
        return restTemplate.postForObject(summaryUrl, httpEntity, ModelSummaryResponse.class);
    }
}
