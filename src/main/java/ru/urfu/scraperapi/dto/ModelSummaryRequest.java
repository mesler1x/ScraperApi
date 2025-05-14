package ru.urfu.scraperapi.dto;

import ru.urfu.scraperapi.jpa.entity.Publication;

import java.util.UUID;

public record ModelSummaryRequest(
        UUID id,
        String title,
        String text
) {

    public static ModelSummaryRequest from(Publication publication) {
        return new ModelSummaryRequest(
                publication.getId(),
                publication.getTitle(),
                publication.getText()
        );
    }
}
