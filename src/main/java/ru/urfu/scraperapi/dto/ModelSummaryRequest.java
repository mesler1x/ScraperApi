package ru.urfu.scraperapi.dto;

import ru.urfu.scraperapi.jpa.entity.Publication;


public record ModelSummaryRequest(
        String title,
        String text
) {

    public static ModelSummaryRequest from(Publication publication) {
        return new ModelSummaryRequest(
                publication.getTitle(),
                publication.getText()
        );
    }
}
