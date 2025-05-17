package ru.urfu.scraperapi.service.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.urfu.scraperapi.dto.ModelSummaryRequest;
import ru.urfu.scraperapi.dto.ModelSummaryResponse;
import ru.urfu.scraperapi.jpa.entity.Publication;

@Service
@RequiredArgsConstructor
public class SummaryModelRestClient {
    private final RestTemplate restTemplate;

    @Value("${scrape.model.summary-url}")
    private String summaryUrl;

    public ModelSummaryResponse getSummaryFromPublication(Publication publication) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var httpEntity = new HttpEntity<>(ModelSummaryRequest.from(publication), headers);
        return restTemplate.postForObject(summaryUrl, httpEntity, ModelSummaryResponse.class);
    }
}
