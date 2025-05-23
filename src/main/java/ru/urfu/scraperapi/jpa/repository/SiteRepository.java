package ru.urfu.scraperapi.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.urfu.scraperapi.jpa.entity.Site;

import java.util.UUID;

@Repository
public interface SiteRepository extends JpaRepository<Site, UUID> {
}
