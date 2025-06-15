package ru.urfu.scraperapi.dto;

import jakarta.persistence.Column;
import ru.urfu.scraperapi.jpa.entity.Publication;

import java.util.UUID;

public record PublicationResponse(
        UUID id,
        String title,
        String text,
        String url,
        String author,
        UUID siteId,
        Long publishedAt,
        Long createdAt,
        Boolean isFake
) {
    public static PublicationResponse of(Publication publication) {
        var siteId = publication.getSite() != null ? publication.getSite().getId() : null;
        return new PublicationResponse(
                publication.getId(),
                publication.getTitle(),
                publication.getText(),
                publication.getUrl(),
                publication.getAuthor(),
                siteId,
                publication.getPublishedAt(),
                publication.getCreatedAt(),
                publication.getIsFake()
        );
    }
}
