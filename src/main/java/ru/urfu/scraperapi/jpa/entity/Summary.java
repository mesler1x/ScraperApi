package ru.urfu.scraperapi.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Summary {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id", nullable = false)
    private Publication publication;

    @Column(nullable = false)
    private String summary;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

}
