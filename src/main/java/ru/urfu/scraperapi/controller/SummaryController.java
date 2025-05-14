package ru.urfu.scraperapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urfu.scraperapi.dto.SummaryResponse;
import ru.urfu.scraperapi.service.SummaryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SummaryController {
    private final SummaryService summaryService;

    @PostMapping("/summary/{publicationId}")
    public SummaryResponse getSummaryByPublicationId(@PathVariable("publicationId") UUID publicationId) {
        return summaryService.getSummaryByPublicationId(publicationId);
    }

    @PostMapping("/summary/{summaryId}/regenerate")
    public SummaryResponse regenerateSummaryByIdAndGet(@PathVariable("summaryId") UUID summaryId) {
        return summaryService.regenerateSummaryByIdAndGet(summaryId);
    }
}
