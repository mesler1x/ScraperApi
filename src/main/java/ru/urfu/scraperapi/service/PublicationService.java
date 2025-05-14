package ru.urfu.scraperapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.urfu.scraperapi.dto.PublicationResponse;
import ru.urfu.scraperapi.jpa.repository.PublicationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationService {
    private final PublicationRepository publicationRepository;

    public List<PublicationResponse> findAll(int limit, int offset) {
        return publicationRepository.findAll(PageRequest.of(offset, limit))
                .stream()
                .map(PublicationResponse::of)
                .toList();
    }

}
