package ru.urfu.scraperapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.urfu.scraperapi.dto.SummaryResponse;
import ru.urfu.scraperapi.service.SummaryService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/summary")
@Tag(name = "Сервис саммари", description = "Получение саммари по публикациям, собраных скрапером")
public class SummaryController {
    private final SummaryService summaryService;


    @PostMapping("/{publicationId}")
    @Operation(summary = "Получить саммари по id публикации")
    public SummaryResponse getSummaryByPublicationId(@PathVariable("publicationId") UUID publicationId) {
        return summaryService.getSummaryByPublicationId(publicationId);
    }

    @PostMapping("/{summaryId}/regenerate")
    @Operation(summary = "Перегенерировать саммари по id саммари")
    public SummaryResponse regenerateSummaryByIdAndGet(@PathVariable("summaryId") UUID summaryId) {
        return summaryService.regenerateSummaryByIdAndGet(summaryId);
    }

    @GetMapping
    @Operation(summary = "Получить все саммари по публикациям")
    public List<SummaryResponse> getAllSummaries() {
        return summaryService.getAllSummaries();
    }
}
