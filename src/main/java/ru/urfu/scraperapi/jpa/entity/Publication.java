package ru.urfu.scraperapi.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class Publication {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;

    @Column(nullable = false)
    private String text;

    private String url;

    private String author;

    @ManyToOne
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    private Site site;

    @Column(name = "created_at")
    private Long createdAt;
}
