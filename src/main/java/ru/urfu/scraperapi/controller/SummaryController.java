package ru.urfu.scraperapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.urfu.scraperapi.dto.SummaryResponse;
import ru.urfu.scraperapi.service.SummaryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/summary")
public class SummaryController {
    private final SummaryService summaryService;

    @PostMapping("/{publicationId}")
    public SummaryResponse getSummaryByPublicationId(@PathVariable("publicationId") UUID publicationId) {
        return summaryService.getSummaryByPublicationId(publicationId);
    }

    @PostMapping("/{summaryId}/regenerate")
    public SummaryResponse regenerateSummaryByIdAndGet(@PathVariable("summaryId") UUID summaryId) {
        return summaryService.regenerateSummaryByIdAndGet(summaryId);
    }

    @GetMapping
    public List<SummaryResponse> getAllSummaries() {
        return summaryService.getAllSummaries();
    }
}
