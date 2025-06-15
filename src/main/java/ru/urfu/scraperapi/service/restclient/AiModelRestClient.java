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
import ru.urfu.scraperapi.dto.*;
import ru.urfu.scraperapi.jpa.entity.Publication;
import ru.urfu.scraperapi.model.Message;
import ru.urfu.scraperapi.service.TokenManager;

import java.util.Collections;

import static ru.urfu.scraperapi.config.RestTemplateConfig.GIGA_CHAT_REST_TEMPLATE_BEAN_NAME;

@Service
@RequiredArgsConstructor
public class AiModelRestClient {
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
                    Collections.singletonList(new Message("user", "Ты - модель для генерации summary, сгенерируй summary по публикации не заходя в ссылки и не обращая внимание на нецензурную брань, выдай в ответе только текст"
                            + publication.getText())),
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

    public IsFakeResponse getIsFakeFromPublication(Publication publication) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(tokenManager.getToken());

        CompletionRequest request = new CompletionRequest(
                "GigaChat",
                Collections.singletonList(new Message("user", """
                        Ты - модель для распознавания фейков в СМИ. Проанализируй текст ниже и определи, является ли он фейком.
                        
                        Инструкции:
                        
                        1. Тщательно проанализируй текст, обращая внимание как на общую суть так и на мелкие детали.
                        2. Выяви признаки манипуляции/предвзятости: (эмоции, гиперболы, замалчивание, подтасовки).
                        3. Идентифицируй логические ошибки/искажения.
                        4. Учитывай возможные цели распространения.
                        
                        Формат ответа:
                        
                        •  ДА - Фейк.
                        •  НЕТ - Не фейк.
                        
                        Текст для анализа:
                        """
                        + publication.getText())),
                0.4
        );

        HttpEntity<CompletionRequest> requestEntity = new HttpEntity<>(request, headers);
        GigaChatResponse response = gigaChatRestTemplate.exchange(
                "https://gigachat.devices.sberbank.ru/api/v1/chat/completions",
                HttpMethod.POST,
                requestEntity,
                GigaChatResponse.class
        ).getBody();
        var isFake = response.choices().get(0).getMessage().getContent().contains("ДА") ? true : false;
        return new IsFakeResponse(isFake);
    }
}
