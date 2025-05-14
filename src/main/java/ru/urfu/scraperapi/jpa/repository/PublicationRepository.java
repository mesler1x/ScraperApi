package ru.urfu.scraperapi.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.urfu.scraperapi.jpa.entity.Publication;

import java.util.UUID;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    @Query("SELECT EXISTS (SELECT s.id FROM Summary s WHERE s.publication.id = :publicationId)")
    boolean isSummaryExists(UUID publicationId);
}
