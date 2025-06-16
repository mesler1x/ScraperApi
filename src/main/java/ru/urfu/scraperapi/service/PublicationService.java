package ru.urfu.scraperapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.urfu.scraperapi.dto.IsFakeResponse;
import ru.urfu.scraperapi.dto.PublicationResponse;
import ru.urfu.scraperapi.jpa.entity.Publication;
import ru.urfu.scraperapi.jpa.repository.PublicationRepository;
import ru.urfu.scraperapi.service.restclient.AiModelRestClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final AiModelRestClient aiModelRestClient;

    public List<PublicationResponse> findAll(int limit, int offset) {
        return publicationRepository.findAll(PageRequest.of(offset, limit, Sort.by("publishedAt").descending()))
                .stream()
                .map(PublicationResponse::of)
                .toList();
    }

    public IsFakeResponse isFake(UUID publicationId) {
        var publication = publicationRepository.findById(publicationId).get();
        var isFakeFromPublication = aiModelRestClient.getIsFakeFromPublication(publication);
        if (isFakeFromPublication.isFake()) {
            publication.setIsFake(true);
        } else {
            publication.setIsFake(false);
        }
        publicationRepository.save(publication);
        return isFakeFromPublication;
    }

    @Scheduled(fixedRate = 1000000L)
    public void setFakes() {
        publicationRepository.findAllPubsWhereIsFakeIsNull().forEach(publication -> {
            isFake(publication.getId());
        });
    }

    public List<PublicationResponse> getSimilarPublications(UUID publicationId, Long limit) {
        return publicationRepository.getSimilarPublications(publicationId, limit).stream()
                .map(PublicationResponse::of)
                .toList();
    }

    public PublicationResponse getById(UUID publicationId) {
        return publicationRepository.findById(publicationId)
                .map(PublicationResponse::of)
                .orElse(null);
    }
}
