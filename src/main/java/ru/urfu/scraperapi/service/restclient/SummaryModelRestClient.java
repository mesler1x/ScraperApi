package ru.urfu.scraperapi.service.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.urfu.scraperapi.dto.ModelSummaryResponse;
import ru.urfu.scraperapi.dto.PublicationResponse;
import ru.urfu.scraperapi.jpa.entity.Publication;

@Service
@RequiredArgsConstructor
public class SummaryModelRestClient {
    private final RestTemplate restTemplate;

    public ModelSummaryResponse getSummaryFromPublication(Publication publication) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var httpEntity = new HttpEntity<>(PublicationResponse.of(publication), headers);
        var url = "http://someurl";//todo
        return restTemplate.postForObject(url, httpEntity, ModelSummaryResponse.class);
    }
}
