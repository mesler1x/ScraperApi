package ru.urfu.scraperapi.service.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.urfu.scraperapi.dto.ModelSummaryResponse;
import ru.urfu.scraperapi.jpa.entity.Publication;

@Service
@RequiredArgsConstructor
public class SummaryModelRestClient {
    //private final RestTemplate restTemplate;

    public ModelSummaryResponse getSummaryFromPublication(Publication publication) {
        return new ModelSummaryResponse("");//fixme
    }
}
