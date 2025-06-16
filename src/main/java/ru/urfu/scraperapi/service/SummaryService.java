package ru.urfu.scraperapi.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.urfu.scraperapi.dto.SummaryResponse;
import ru.urfu.scraperapi.jpa.entity.Publication;
import ru.urfu.scraperapi.jpa.entity.Summary;
import ru.urfu.scraperapi.jpa.repository.PublicationRepository;
import ru.urfu.scraperapi.jpa.repository.SummaryRepository;
import ru.urfu.scraperapi.service.restclient.AiModelRestClient;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SummaryService {

    private final SummaryRepository summaryRepository;
    private final PublicationRepository publicationRepository;
    private final AiModelRestClient aiModelRestClient;

    public SummaryResponse getSummaryByPublicationId(UUID publicationId) {
        return publicationRepository.findById(publicationId)
                .map(publication -> {
                    if (publicationRepository.isSummaryNotExists(publicationId)) {
                        var summary = aiModelRestClient.getSummaryFromPublication(publication);
                        var summaryEntity = new Summary();
                        summaryEntity.setPublication(publication);
                        summaryEntity.setSummary(summary.completion());
                        summaryEntity.setCreatedAt(Instant.now().toEpochMilli());
                        return SummaryResponse.of(
                                summaryRepository.save(summaryEntity)
                        );
                    }

                    return SummaryResponse.of(
                            summaryRepository.findByPublication(publication)
                    );
                })
                .orElseThrow(() -> new EntityNotFoundException("Publication not found with id: " + publicationId));
    }

    public SummaryResponse regenerateSummaryByIdAndGet(UUID summaryId) {
        log.info("start regenerating summary process");
        return summaryRepository.findById(summaryId)
                .map(existingSummary -> {
                    Publication publication = existingSummary.getPublication();
                    var newSummary = aiModelRestClient.getSummaryFromPublication(publication);
                    existingSummary.setSummary(newSummary.completion());
                    return summaryRepository.save(existingSummary);
                })
                .map(SummaryResponse::of)
                .orElseThrow(() -> new EntityNotFoundException("Summary not found with id: " + summaryId));
    }

    @Scheduled(fixedRateString = "${scrape.cron-job-generation-rate-ms}", initialDelay = 5000)
    public void processSummaryGeneration() {
        var publicationsWithoutSummary = publicationRepository.findPublicationsWithoutSummary();
        log.info("Start process summary generation for {} publications", publicationsWithoutSummary.size());
        var startTime = Instant.now().toEpochMilli();
        publicationsWithoutSummary
                .forEach(publication -> {
                    var summary = aiModelRestClient.getSummaryFromPublication(publication);
                    var summaryEntity = new Summary();
                    summaryEntity.setPublication(publicationRepository.findById(publication.getId()).get());
                    summaryEntity.setSummary(summary.completion());
                    summaryEntity.setCreatedAt(Instant.now().toEpochMilli());
                    summaryRepository.save(summaryEntity);
                });
        var endTime = Instant.now().toEpochMilli();
        log.info("Finish process summary generation for [{}] publications, time taken [{} sec]", publicationsWithoutSummary.size(), (endTime - startTime) / 1000.0);
    }

    public List<SummaryResponse> getAllSummaries() {
        return summaryRepository.findAll().stream()
                .map(SummaryResponse::of)
                .toList();
    }
}
