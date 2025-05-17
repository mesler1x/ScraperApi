package ru.urfu.scraperapi.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.urfu.scraperapi.jpa.entity.Publication;

import java.util.List;
import java.util.UUID;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    @Query("SELECT NOT EXISTS (SELECT s.id FROM Summary s WHERE s.publication.id = :publicationId)")
    boolean isSummaryNotExists(UUID publicationId);

    @Query("SELECT p FROM Publication p LEFT JOIN Summary s ON p.id = s.publication.id WHERE s IS NULL")
    List<Publication> findPublicationsWithoutSummary();

}
