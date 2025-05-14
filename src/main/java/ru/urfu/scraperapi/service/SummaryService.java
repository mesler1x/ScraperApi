package ru.urfu.scraperapi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.urfu.scraperapi.dto.ModelSummaryResponse;
import ru.urfu.scraperapi.dto.SummaryResponse;
import ru.urfu.scraperapi.jpa.entity.Publication;
import ru.urfu.scraperapi.jpa.entity.Summary;
import ru.urfu.scraperapi.jpa.repository.PublicationRepository;
import ru.urfu.scraperapi.jpa.repository.SummaryRepository;
import ru.urfu.scraperapi.service.restclient.SummaryModelRestClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SummaryService {

    private final SummaryRepository summaryRepository;
    private final PublicationRepository publicationRepository;
    private final SummaryModelRestClient summaryModelRestClient;

    public SummaryResponse getSummaryByPublicationId(UUID publicationId) {
        return publicationRepository.findById(publicationId)
                .map(publication -> {
                    if (publication.getSummary() == null) {
                        var summary = summaryModelRestClient.getSummaryFromPublication(publication);
                        var entity = new Summary();
                        entity.setPublication(publication);
                        entity.setSummary(summary.completion());
                        return SummaryResponse.of(
                                summaryRepository.save(entity)
                        );
                    }

                    return SummaryResponse.of(
                            summaryRepository.findByPublication(publication)
                    );
                })
                .orElseThrow(() -> new EntityNotFoundException("Publication not found with id: " + publicationId));
    }

    public SummaryResponse regenerateSummaryByIdAndGet(UUID summaryId) {
        return summaryRepository.findById(summaryId)
                .map(existingSummary -> {
                    Publication publication = existingSummary.getPublication();
                    var newSummary = summaryModelRestClient.getSummaryFromPublication(publication);
                    existingSummary.setSummary(newSummary.completion());
                    return summaryRepository.save(existingSummary);
                })
                .map(SummaryResponse::of)
                .orElseThrow(() -> new EntityNotFoundException("Summary not found with id: " + summaryId));
    }
}
