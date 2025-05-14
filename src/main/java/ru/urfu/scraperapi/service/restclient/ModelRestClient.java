package ru.urfu.scraperapi.service.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.urfu.scraperapi.dto.ModelSummaryResponse;
import ru.urfu.scraperapi.jpa.entity.Publication;

@Service
@RequiredArgsConstructor
public class ModelRestClient {
    //private final RestTemplate restTemplate;

    public ModelSummaryResponse getSummaryFromPublication(Publication publication) {
        return new ModelSummaryResponse("");//fixme
    }
}
