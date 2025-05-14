package ru.urfu.scraperapi.dto;

import ru.urfu.scraperapi.jpa.entity.Publication;
import ru.urfu.scraperapi.jpa.entity.Summary;

import java.util.UUID;

public record SummaryResponse(
        UUID id,
        UUID publicationId,
        String summary,
        Long createdAt
) {
    public static SummaryResponse of(Summary summary) {
        return new SummaryResponse(
                summary.getId(),
                summary.getPublication().getId(),
                summary.getSummary(),
                summary.getCreatedAt()
        );
    }
}
